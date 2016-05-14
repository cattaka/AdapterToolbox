package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import net.cattaka.android.snippets.adapter.CustomRecyclerAdapter;
import net.cattaka.android.snippets.example.adapter.SimpleStringAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class SimpleStringExampleActivity extends AppCompatActivity implements
        CustomRecyclerAdapter.OnItemClickListener<SimpleStringAdapter.ViewHolder, String>,
        CustomRecyclerAdapter.OnItemLongClickListener<SimpleStringAdapter.ViewHolder, String> {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_string_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        { // set adapter
            List<String> items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                items.add("item " + i);
            }
            SimpleStringAdapter adapter = new SimpleStringAdapter(this, items);
            adapter.setOnItemClickListener(this);
            adapter.setOnItemLongClickListener(this);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, CustomRecyclerAdapter<SimpleStringAdapter.ViewHolder, String> adapter, int position, int id, SimpleStringAdapter.ViewHolder viewHolder) {
        if (parent.getId() == R.id.recycler) {
            String item = adapter.getItemAt(position);
            Toast.makeText(this, item + " is clicked.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClick(RecyclerView parent, CustomRecyclerAdapter<SimpleStringAdapter.ViewHolder, String> adapter, int position, int id, View view, SimpleStringAdapter.ViewHolder viewHolder) {
        if (parent.getId() == R.id.recycler) {
            String item = adapter.getItemAt(position);
            Toast.makeText(this, item + " is long clicked.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
