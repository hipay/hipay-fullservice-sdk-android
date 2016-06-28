package com.hipay.hipayfullservice.screen.fragment;

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

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.client.GatewayClient;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.PaymentProductsCallback;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.screen.activity.PaymentFormActivity;
import com.hipay.hipayfullservice.screen.adapter.PaymentProductsAdapter;
import com.hipay.hipayfullservice.screen.helper.TransitionHelper;
import com.hipay.hipayfullservice.screen.model.CustomTheme;
import com.hipay.hipayfullservice.screen.widget.OffsetDecoration;

import java.util.List;

/**
 * Created by nfillion on 26/02/16.
 */

public class PaymentProductsFragment extends Fragment {

    private static final String STATE_IS_LOADING = "isLoading";

    private PaymentProductsAdapter mAdapter;
    private GatewayClient mGatewayClient;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    private List<PaymentProduct> mPaymentProducts;

    protected boolean mLoadingMode;
    protected int mCurrentLoading = -1;

    public static PaymentProductsFragment newInstance(Bundle paymentPageRequestBundle, Bundle customTheme) {

        PaymentProductsFragment fragment = new PaymentProductsFragment();

        Bundle bundle = new Bundle();
        bundle.putBundle(PaymentPageRequest.TAG, paymentPageRequestBundle);

        bundle.putBundle(CustomTheme.TAG, customTheme);

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
        mCurrentLoading = 5;
        mGatewayClient.getPaymentProducts(paymentPageRequest, new PaymentProductsCallback() {
            @Override
            public void onSuccess(List<PaymentProduct> paymentProducts) {

                if (paymentProducts != null && !paymentProducts.isEmpty()) {

                    mPaymentProducts = paymentProducts;
                    mAdapter.updatePaymentProducts(mPaymentProducts, paymentPageRequest.isPaymentCardGroupingEnabled());
                    //notifyItemChanged();

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
                            .setNegativeButton(R.string.error_button_dismiss, dialogClickListener)
                            .setCancelable(false)
                            .show();
                }

                cancelOperations();
            }

            @Override
            public void onError(Exception error) {

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
                            .setNegativeButton(R.string.error_button_dismiss, dialogClickListener)
                            .setCancelable(false)
                            .show();
                }

                // an error occurred
                cancelOperations();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //TODO check when we fire this one
        super.onViewCreated(view, savedInstanceState);

        final Bundle paymentPageRequestBundle = getArguments().getBundle(PaymentPageRequest.TAG);
        final Bundle customThemeBundle = getArguments().getBundle(CustomTheme.TAG);

        mProgressBar = (ProgressBar)view.findViewById(R.id.progress);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.hpf_light)));

        } else {
            mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.hpf_light), PorterDuff.Mode.SRC_IN);
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.products);
        setUpProductGrid(mRecyclerView, paymentPageRequestBundle, customThemeBundle);

        if (savedInstanceState == null) {

            launchRequest();

        } else {

            PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(paymentPageRequestBundle);

            if (mPaymentProducts != null && !mPaymentProducts.isEmpty()) {
                mAdapter.updatePaymentProducts(mPaymentProducts, paymentPageRequest.isPaymentCardGroupingEnabled());
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

    private void setUpProductGrid(final RecyclerView categoriesView, final Bundle paymentPageRequestBundle, final Bundle customThemeBundle) {

        final int spacing = getContext().getResources()
                .getDimensionPixelSize(R.dimen.spacing_nano);
        categoriesView.addItemDecoration(new OffsetDecoration(spacing));

        mAdapter = new PaymentProductsAdapter(getActivity());
        mAdapter.setOnItemClickListener(
                new PaymentProductsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Activity activity = getActivity();
                        startPaymentFormActivityWithTransition(activity,
                                v.findViewById(R.id.payment_product_title),
                                paymentPageRequestBundle,
                                customThemeBundle,
                                mAdapter.getItem(position));
                    }
                });

        categoriesView.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {

        getActivity().supportStartPostponedEnterTransition();
        super.onResume();

        //TODO
        setLoadingMode(mLoadingMode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startPaymentFormActivityWithTransition(Activity activity, View toolbar, Bundle paymentPageRequestBundle, Bundle customThemeBundle,
                                                        PaymentProduct paymentProduct) {

        final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(toolbar, activity.getString(R.string.transition_toolbar)));
        @SuppressWarnings("unchecked")
        ActivityOptionsCompat sceneTransitionAnimation = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, pairs);

         //Start the activity with the participants, animating from one to the other.
        final Bundle transitionBundle = sceneTransitionAnimation.toBundle();

        Intent startIntent = PaymentFormActivity.getStartIntent(activity, paymentPageRequestBundle, customThemeBundle, paymentProduct);
        ActivityCompat.startActivityForResult(activity,
                startIntent,
                PaymentPageRequest.REQUEST_ORDER,
                transitionBundle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(STATE_IS_LOADING, mLoadingMode);
    }

    public void cancelOperations() {

        if (mGatewayClient != null) {
            mGatewayClient.cancelOperation();
            mGatewayClient = null;
        }

        setLoadingMode(false);
    }
}
