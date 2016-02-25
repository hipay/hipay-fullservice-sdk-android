package com.hipay.hipayfullservice.example.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.example.R;
import com.hipay.hipayfullservice.example.adapter.PaymentProductAdapter;
import com.hipay.hipayfullservice.example.widget.OffsetDecoration;

/**
 * Created by nfillion on 26/02/16.
 */

public class PaymentScreenFragment extends Fragment {

    private PaymentProductAdapter mAdapter;
    private static final int REQUEST_CATEGORY = 0x2300;

    public static Fragment newInstance() {
        return new PaymentScreenFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_products, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setUpQuizGrid((RecyclerView) view.findViewById(R.id.categories));
        super.onViewCreated(view, savedInstanceState);
    }

    private void setUpQuizGrid(RecyclerView categoriesView) {

        //TODO do nothing

        final int spacing = getContext().getResources()
                .getDimensionPixelSize(R.dimen.spacing_nano);
        categoriesView.addItemDecoration(new OffsetDecoration(spacing));

        mAdapter = new PaymentProductAdapter(getActivity());
        mAdapter.setOnItemClickListener(
                new PaymentProductAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Activity activity = getActivity();
                        startQuizActivityWithTransition(activity,
                                v.findViewById(R.id.category_title),
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

        //TODO nothing

        //if (requestCode == REQUEST_CATEGORY && resultCode == R.id.solved) {
            //mAdapter.notifyItemChanged(data.getStringExtra(JsonAttributes.ID));
        //}
        //super.onActivityResult(requestCode, resultCode, data);
    }

    private void startQuizActivityWithTransition(Activity activity, View toolbar,
                                                 PaymentProduct category) {

        // do nothing here

        //final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                //new Pair<>(toolbar, activity.getString(R.string.transition_toolbar)));
        //@SuppressWarnings("unchecked")
        //ActivityOptionsCompat sceneTransitionAnimation = ActivityOptionsCompat
                //.makeSceneTransitionAnimation(activity, pairs);

        // Start the activity with the participants, animating from one to the other.
        //final Bundle transitionBundle = sceneTransitionAnimation.toBundle();
        //Intent startIntent = QuizActivity.getStartIntent(activity, category);
        //ActivityCompat.startActivityForResult(activity,
                //startIntent,
                //REQUEST_CATEGORY,
                //transitionBundle);
    }

}
