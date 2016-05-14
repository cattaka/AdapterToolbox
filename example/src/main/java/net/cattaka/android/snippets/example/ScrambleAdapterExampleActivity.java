package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.snippets.adapter.ForwardingListener;
import net.cattaka.android.snippets.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.example.adapter.factory.CodeLableViewHolderFactory;
import net.cattaka.android.snippets.example.adapter.factory.SimpleNumberViewHolderFactory;
import net.cattaka.android.snippets.example.adapter.factory.SimpleStringViewHolderFactory;
import net.cattaka.android.snippets.example.data.OrdinalLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class ScrambleAdapterExampleActivity extends AppCompatActivity implements
        ForwardingListener.OnItemClickListener<ScrambleAdapter, RecyclerView.ViewHolder>,
        ForwardingListener.OnItemLongClickListener<ScrambleAdapter, RecyclerView.ViewHolder> {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scramble_adapter_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        { // set adapter
            List<Object> items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                switch (i % 4) {
                    case 0:
                        items.add(i);
                        break;
                    case 1:
                        items.add(String.valueOf(i));
                        break;
                    case 2:
                        OrdinalLabel[] ols = OrdinalLabel.values();
                        items.add(ols[(i / 4) % ols.length]);
                    default:
                        items.add(new Object());
                        break;
                }
            }
            ScrambleAdapter adapter = new ScrambleAdapter(this, items,
                    new SimpleStringViewHolderFactory(),
                    new SimpleNumberViewHolderFactory(),
                    new CodeLableViewHolderFactory(getResources())
            );
            adapter.setOnItemClickListener(this);
            adapter.setOnItemLongClickListener(this);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, ScrambleAdapter adapter, int position, int id, RecyclerView.ViewHolder vh) {
        if (parent.getId() == R.id.recycler) {
            if (vh instanceof SimpleStringViewHolderFactory.ViewHolder) {
                String item = (String) adapter.getItemAt(position);
                Snackbar.make(vh.itemView, "String " + item + " is clicked.", Snackbar.LENGTH_SHORT).show();
            } else if (vh instanceof SimpleNumberViewHolderFactory.ViewHolder) {
                Number item = (Number) adapter.getItemAt(position);
                Snackbar.make(vh.itemView, "Number " + item + " is clicked.", Snackbar.LENGTH_SHORT).show();
            } else if (vh instanceof CodeLableViewHolderFactory.ViewHolder) {
                OrdinalLabel item = (OrdinalLabel) adapter.getItemAt(position);
                String text = item.getLabel(getResources()) + "(" + item.getCode() + ")";
                if (id == R.id.text_code) {
                    Snackbar.make(vh.itemView, "The code of " + text + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(vh.itemView, "The label of " + text + " is clicked.", Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onItemLongClick(RecyclerView parent, ScrambleAdapter adapter, int position, int id, View view, RecyclerView.ViewHolder vh) {
        if (parent.getId() == R.id.recycler) {
            if (vh instanceof SimpleStringViewHolderFactory.ViewHolder) {
                String item = (String) adapter.getItemAt(position);
                Snackbar.make(vh.itemView, "String " + item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
            } else if (vh instanceof SimpleNumberViewHolderFactory.ViewHolder) {
                Number item = (Number) adapter.getItemAt(position);
                Snackbar.make(vh.itemView, "Number " + item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
            } else if (vh instanceof CodeLableViewHolderFactory.ViewHolder) {
                OrdinalLabel item = (OrdinalLabel) adapter.getItemAt(position);
                String text = item.getLabel(getResources()) + "(" + item.getCode() + ")";
                if (id == R.id.text_code) {
                    Snackbar.make(vh.itemView, "The code of " + text + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(vh.itemView, "The label of " + text + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                }
            }
            return true;
        }
        return false;
    }
}
