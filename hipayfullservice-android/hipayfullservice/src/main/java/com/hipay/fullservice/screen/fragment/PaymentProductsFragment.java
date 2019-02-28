package com.hipay.fullservice.screen.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.PaymentProductsCallback;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.screen.activity.PaymentFormActivity;
import com.hipay.fullservice.screen.adapter.PaymentProductsAdapter;
import com.hipay.fullservice.screen.helper.TransitionHelper;
import com.hipay.fullservice.screen.model.CustomTheme;
import com.hipay.fullservice.screen.widget.OffsetDecoration;

import java.util.Iterator;
import java.util.List;

/**
 * Created by nfillion on 26/02/16.
 */

public class PaymentProductsFragment extends Fragment implements PaymentProductsAdapter.OnItemClickListener  {

    private static final String STATE_IS_LOADING = "isLoading";

    private PaymentProductsAdapter mAdapter;
    private GatewayClient mGatewayClient;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    private List<PaymentProduct> paymentProducts;

    protected boolean mLoadingMode;
    protected int mCurrentLoading = -1;

    public static PaymentProductsFragment newInstance(Bundle paymentPageRequestBundle, String signature, Bundle customTheme) {

        PaymentProductsFragment fragment = new PaymentProductsFragment();

        Bundle bundle = new Bundle();
        bundle.putBundle(PaymentPageRequest.TAG, paymentPageRequestBundle);

        bundle.putBundle(CustomTheme.TAG, customTheme);
        bundle.putString(GatewayClient.SIGNATURE_TAG, signature);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (mGatewayClient != null) {
            mGatewayClient.reLaunchOperations(mCurrentLoading);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //necessary to handle the request
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_payment_products, container, false);
    }

    private void launchRequest() {

        setLoadingMode(true);

        final Bundle paymentPageRequestBundle = getArguments().getBundle(PaymentPageRequest.TAG);
        final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(paymentPageRequestBundle);

        mGatewayClient = new GatewayClient(getActivity());
        mCurrentLoading = AbstractClient.RequestLoaderId.PaymentProductsReqLoaderId.getIntegerValue();
        mGatewayClient.getPaymentProducts(paymentPageRequest, new PaymentProductsCallback() {
            @Override
            public void onSuccess(List<PaymentProduct> pProducts) {

                cancelOperations();

                if (pProducts != null && !pProducts.isEmpty()) {

                    paymentProducts = updatedPaymentProducts(pProducts, paymentPageRequest.isPaymentCardGroupingEnabled());
                    if (paymentProducts != null) {
                        mAdapter.updatePaymentProducts(paymentProducts);
                    }

                } else {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            if (getActivity() != null) {
                                getActivity().finish();
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.error_title_default)
                            .setMessage(R.string.error_body_payment_products)
                            .setNegativeButton(R.string.button_ok, dialogClickListener)
                            .setCancelable(false)
                            .show();
                }

            }

            @Override
            public void onError(Exception error) {

                // an error occurred
                cancelOperations();

                if (getActivity() != null) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            if (getActivity() != null) {
                                getActivity().finish();
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.error_title_default)
                            .setMessage(R.string.error_body_default)
                            .setNegativeButton(R.string.button_ok, dialogClickListener)
                            .setCancelable(false)
                            .show();
                }

            }
        });
    }

    private List<PaymentProduct> updatedPaymentProducts(List<PaymentProduct> paymentProducts, Boolean isGroupingCard) {

        boolean atLeastOneCard = false;
        boolean atLeastOneNoCard = false;

        if (isGroupingCard != null && isGroupingCard.equals(Boolean.TRUE)) {

            Iterator<PaymentProduct> iter = paymentProducts.iterator();
            while (iter.hasNext()) {

                PaymentProduct p = iter.next();
                if (p.isTokenizable()) {
                    iter.remove();
                    atLeastOneCard = true;
                } else {
                    atLeastOneNoCard = true;
                }
            }

            if (atLeastOneCard) {

                PaymentProduct cardProduct = new PaymentProduct();
                cardProduct.setCode(PaymentProduct.PaymentProductCategoryCodeCard);
                cardProduct.setPaymentProductDescription(getActivity().getString(R.string.payment_product_card_description));
                cardProduct.setTokenizable(true);

                // there are other payment products
                if (atLeastOneNoCard) {

                    paymentProducts.add(0, cardProduct);

                } else {

                    // just one card, represented by card product
                    onClick(null, cardProduct);
                    return null;
                }
            }

        } else {

            // do something only if there is one tokenizable
            if (paymentProducts.size() == 1) {

                PaymentProduct paymentProduct = paymentProducts.get(0);
                if (paymentProduct.isTokenizable()) {

                    onClick(null, paymentProduct);
                    return null;
                }
            }
        }

        return paymentProducts;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = (ProgressBar)view.findViewById(R.id.progress);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.hpf_light)));

        } else {
            mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.hpf_light), PorterDuff.Mode.SRC_IN);
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.products);
        setUpProductGrid(mRecyclerView);

        if (savedInstanceState == null) {

            launchRequest();

        } else {

            if (paymentProducts != null && !paymentProducts.isEmpty()) {
                mAdapter.updatePaymentProducts(paymentProducts);
            }
        }
    }

    private void setLoadingMode(boolean loadingMode) {

        if (loadingMode) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);

        } else {

            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        mLoadingMode = loadingMode;
    }

    private void setUpProductGrid(final RecyclerView categoriesView) {

        final int spacing = getContext().getResources()
                .getDimensionPixelSize(R.dimen.spacing_nano);
        categoriesView.addItemDecoration(new OffsetDecoration(spacing));

        mAdapter = new PaymentProductsAdapter(getActivity());
        mAdapter.setOnItemClickListener(this);

        categoriesView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view, PaymentProduct paymentProduct) {

        final Bundle paymentPageRequestBundle = getArguments().getBundle(PaymentPageRequest.TAG);
        final Bundle customThemeBundle = getArguments().getBundle(CustomTheme.TAG);
        final String signature = getArguments().getString(GatewayClient.SIGNATURE_TAG);

        Activity activity = getActivity();
        startPaymentFormActivityWithTransition(activity, view == null ? null :
                        view.findViewById(R.id.payment_product_title),
                paymentPageRequestBundle,
                customThemeBundle,
                paymentProduct,
                signature);
    }

    @Override
    public void onResume() {

        getActivity().supportStartPostponedEnterTransition();
        super.onResume();

        setLoadingMode(mLoadingMode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startPaymentFormActivityWithTransition(Activity activity, View toolbar, Bundle paymentPageRequestBundle, Bundle customThemeBundle,
                                                        PaymentProduct paymentProduct, String signature) {

        Bundle transitionBundle = null;

        if (toolbar != null) {
            final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                    new Pair<>(toolbar, activity.getString(R.string.transition_toolbar)));
            @SuppressWarnings("unchecked")
            ActivityOptionsCompat sceneTransitionAnimation = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity, pairs);

            //Start the activity with the participants, animating from one to the other.
            transitionBundle = sceneTransitionAnimation.toBundle();
        }

        Intent startIntent = PaymentFormActivity.getStartIntent(activity, paymentPageRequestBundle, customThemeBundle, paymentProduct, signature);
        ActivityCompat.startActivityForResult(activity,
                startIntent,
                PaymentPageRequest.REQUEST_ORDER,
                //transitionBundle);
                //avoid glitch problem
                null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(STATE_IS_LOADING, mLoadingMode);
    }

    public void cancelOperations() {

        if (mGatewayClient != null) {
            mGatewayClient.cancelOperation(getActivity());
            mGatewayClient = null;
        }

        setLoadingMode(false);
    }

    public List<PaymentProduct> getPaymentProducts() {
        return paymentProducts;
    }

    public void setPaymentProducts(List<PaymentProduct> paymentProducts) {
        this.paymentProducts = paymentProducts;
    }
}
