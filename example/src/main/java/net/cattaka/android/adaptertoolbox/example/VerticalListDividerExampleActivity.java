package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.cattaka.android.adaptertoolbox.decoration.VerticalListDividerItemDecoration;
import net.cattaka.android.adaptertoolbox.example.adapter.SimpleStringAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/12/11.
 */

public class VerticalListDividerExampleActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_divider_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        { // set adapter
            List<String> items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                items.add("item " + i);
            }
            SimpleStringAdapter adapter = new SimpleStringAdapter(this, items, null);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.addItemDecoration(new VerticalListDividerItemDecoration(this, false, R.drawable.vertical_list_divider_rgb));
        }
    }
}
