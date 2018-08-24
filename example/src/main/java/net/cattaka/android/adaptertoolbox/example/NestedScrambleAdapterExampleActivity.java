package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.CodeLableViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.NestedScrambleInfoViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.SimpleNumberViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.SimpleStringViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.data.NestedScrambleInfo;
import net.cattaka.android.adaptertoolbox.example.data.OrdinalLabel;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class NestedScrambleAdapterExampleActivity extends AppCompatActivity {

    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
    };

    SnackbarLogic mSnackbarLogic = new SnackbarLogic();

    RecyclerView mRecyclerView;
    ScrambleAdapter<Object> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scramble_adapter_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        { // set adapter
            List<ScrambleAdapter.AbsViewHolderFactory<? extends RecyclerView.ViewHolder>> viewHolderViewHolderFactories = new ArrayList<>();
            viewHolderViewHolderFactories.add(new SimpleStringViewHolderFactory());
            viewHolderViewHolderFactories.add(new SimpleNumberViewHolderFactory());
            viewHolderViewHolderFactories.add(new CodeLableViewHolderFactory(getResources()));

            List<Object> items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                List<Object> nestedItems = new ArrayList<>();
                nestedItems.add("<-- Slide me!!");
                for (int j = 0; j < 12; j++) {
                    switch (j % 4) {
                        case 0:
                            nestedItems.add(i);
                            break;
                        case 1:
                            nestedItems.add(String.valueOf(i));
                            break;
                        case 2:
                            OrdinalLabel[] ols = OrdinalLabel.values();
                            nestedItems.add(ols[(j / 4) % ols.length]);
                        default:
                            nestedItems.add(new Object());
                            break;
                    }
                }
                NestedScrambleInfo item = new NestedScrambleInfo(
                        viewHolderViewHolderFactories,
                        null,
                        nestedItems);
                item.setListenerRelay(new NestedListenerRelay(item));
                items.add(item);
            }
            mAdapter = new ScrambleAdapter<>(this, items,
                    mListenerRelay,
                    new NestedScrambleInfoViewHolderFactory()
            );
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    class NestedListenerRelay extends ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> {
        NestedScrambleInfo item;

        public NestedListenerRelay(NestedScrambleInfo item) {
            this.item = item;
        }

        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler_nested) {
                String row = "Row=" + mAdapter.getItems().indexOf(item);
                if (viewHolder instanceof SimpleStringViewHolderFactory.ViewHolder) {
                    String item = (String) adapter.getItemAt(viewHolder.getAdapterPosition());
                    mSnackbarLogic.make(viewHolder.itemView, row + ", String " + item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof SimpleNumberViewHolderFactory.ViewHolder) {
                    Number item = (Number) adapter.getItemAt(viewHolder.getAdapterPosition());
                    mSnackbarLogic.make(viewHolder.itemView, row + ", Number" + item + "is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof CodeLableViewHolderFactory.ViewHolder) {
                    OrdinalLabel item = (OrdinalLabel) adapter.getItemAt(viewHolder.getAdapterPosition());
                    String text = item.getLabel(getResources()) + "(" + item.getCode() + ")";
                    if (view.getId() == R.id.text_code) {
                        mSnackbarLogic.make(viewHolder.itemView, row + ", The code of " + text + " is clicked.", Snackbar.LENGTH_SHORT).show();
                    } else if (view.getId() == R.id.text_label) {
                        mSnackbarLogic.make(viewHolder.itemView, row + ", The label of " + text + " is clicked.", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public boolean onLongClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                String row = "Row=" + mAdapter.getItems().indexOf(item);
                if (viewHolder instanceof SimpleStringViewHolderFactory.ViewHolder) {
                    String item = (String) adapter.getItemAt(viewHolder.getAdapterPosition());
                    mSnackbarLogic.make(view, row + ", String " + item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof SimpleNumberViewHolderFactory.ViewHolder) {
                    Number item = (Number) adapter.getItemAt(viewHolder.getAdapterPosition());
                    mSnackbarLogic.make(view, row + ", Number " + item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof CodeLableViewHolderFactory.ViewHolder) {
                    OrdinalLabel item = (OrdinalLabel) adapter.getItemAt(viewHolder.getAdapterPosition());
                    String text = item.getLabel(getResources()) + "(" + item.getCode() + ")";
                    if (view.getId() == R.id.text_code) {
                        mSnackbarLogic.make(view, row + ", The code of " + text + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                    } else if (view.getId() == R.id.text_label) {
                        mSnackbarLogic.make(view, row + ", The label of " + text + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
            return false;
        }
    }
}
