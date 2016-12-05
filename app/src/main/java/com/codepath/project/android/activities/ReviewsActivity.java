package com.codepath.project.android.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.project.android.R;
import com.codepath.project.android.adapter.ReviewsAdapter;
import com.codepath.project.android.helpers.Constants;
import com.codepath.project.android.helpers.ItemClickSupport;
import com.codepath.project.android.model.Product;
import com.codepath.project.android.model.Review;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvReviews)
    RecyclerView rvReviews;

    List<Review> reviews;
    ReviewsAdapter reviewsAdapter;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);
        setToolbar();
        getAllReviews();
        setUpRecyclerView();
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(5);
    }

    private void getAllReviews(){
        String productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ParseQuery<Product> query = ParseQuery.getQuery(Product.class);
        query.getInBackground(productId, (p, e) -> {
            if (e == null) {
                product = p;
                TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
                mTitle.setText(product.getName());
                ParseQuery<Review> reviewQuery = ParseQuery.getQuery(Review.class);
                reviewQuery.include("user");
                reviewQuery.whereEqualTo("product", product);
                reviewQuery.addDescendingOrder("updatedAt");
                reviewQuery.findInBackground((reviewList, err) -> {
                    if (err == null) {
                        reviews.addAll(reviewList);
                        reviewsAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ReviewsActivity.this, "parse error", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(ReviewsActivity.this, "parse error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView() {
        reviews = new ArrayList<>();
        reviewsAdapter = new ReviewsAdapter(this, reviews);
        rvReviews.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        rvReviews.setAdapter(reviewsAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.scrollToPosition(0);
        rvReviews.setLayoutManager(mLayoutManager);
        rvReviews.setNestedScrollingEnabled(false);
        ItemClickSupport.addTo(rvReviews).setOnItemClickListener(
                (recyclerView, position, v) -> {
                    Intent intent = new Intent(ReviewsActivity.this, DetailedReviewActivity.class);
                    intent.putExtra("reviewId", reviews.get(position).getObjectId());
                    startActivity(intent);
                }
        );
    }
}
