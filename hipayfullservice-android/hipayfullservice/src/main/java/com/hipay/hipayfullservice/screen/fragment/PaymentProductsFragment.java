package com.hipay.hipayfullservice.screen.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.screen.activity.PaymentFormActivity;
import com.hipay.hipayfullservice.screen.adapter.PaymentProductsAdapter;
import com.hipay.hipayfullservice.screen.helper.TransitionHelper;
import com.hipay.hipayfullservice.screen.widget.OffsetDecoration;

/**
 * Created by nfillion on 26/02/16.
 */

public class PaymentProductsFragment extends Fragment {

    private PaymentProductsAdapter mAdapter;

    public static PaymentProductsFragment newInstance(Bundle paymentPageRequestBundle) {

        PaymentProductsFragment fragment = new PaymentProductsFragment();
        fragment.setArguments(paymentPageRequestBundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_products, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setUpProductGrid((RecyclerView) view.findViewById(R.id.products));
        super.onViewCreated(view, savedInstanceState);
    }

    private void setUpProductGrid(RecyclerView categoriesView) {

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
                                getArguments(),
                                mAdapter.getItem(position));
                    }
                });

        categoriesView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {

        getActivity().supportStartPostponedEnterTransition();
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //TODO nothing for now
    }

    private void startPaymentFormActivityWithTransition(Activity activity, View toolbar, Bundle paymentPageRequestBundle,
                                                        PaymentProduct paymentProduct) {

        final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(toolbar, activity.getString(R.string.transition_toolbar)));
        @SuppressWarnings("unchecked")
        ActivityOptionsCompat sceneTransitionAnimation = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, pairs);

         //Start the activity with the participants, animating from one to the other.
        final Bundle transitionBundle = sceneTransitionAnimation.toBundle();

        Intent startIntent = PaymentFormActivity.getStartIntent(activity, paymentPageRequestBundle, paymentProduct);
        ActivityCompat.startActivityForResult(activity,
                startIntent,
                PaymentPageRequest.REQUEST_ORDER,
                transitionBundle);
    }

}
