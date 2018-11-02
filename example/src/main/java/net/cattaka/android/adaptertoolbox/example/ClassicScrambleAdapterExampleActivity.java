package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.cattaka.android.adaptertoolbox.classic.AdapterConverter;
import net.cattaka.android.adaptertoolbox.classic.ClassicScrambleAdapter;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.CodeLableViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.SimpleNumberViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.SimpleStringViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.data.OrdinalLabel;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class ClassicScrambleAdapterExampleActivity extends AppCompatActivity {
    ClassicListenerRelay mListenerRelay = new ClassicListenerRelay() {
        @Override
        public void onClick(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull View view) {
            if (adapterView.getId() == R.id.list) {
                if (vh instanceof SimpleStringViewHolderFactory.ViewHolder) {
                    String item = (String) adapter.getItem(position);
                    mSnackbarLogic.make(vh.itemView, "String " + item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (vh instanceof SimpleNumberViewHolderFactory.ViewHolder) {
                    Number item = (Number) adapter.getItem(position);
                    mSnackbarLogic.make(vh.itemView, "Number " + item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (vh instanceof CodeLableViewHolderFactory.ViewHolder) {
                    OrdinalLabel item = (OrdinalLabel) adapter.getItem(position);
                    String text = item.getLabel(getResources()) + "(" + item.getCode() + ")";
                    if (view.getId() == R.id.text_code) {
                        mSnackbarLogic.make(vh.itemView, "The code of " + text + " is clicked.", Snackbar.LENGTH_SHORT).show();
                    } else if (view.getId() == R.id.text_label) {
                        mSnackbarLogic.make(vh.itemView, "The label of " + text + " is clicked.", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public boolean onLongClick(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull View view) {
            if (adapterView.getId() == R.id.list) {
                if (vh instanceof SimpleStringViewHolderFactory.ViewHolder) {
                    String item = (String) adapter.getItem(position);
                    mSnackbarLogic.make(view, "String " + item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (vh instanceof SimpleNumberViewHolderFactory.ViewHolder) {
                    Number item = (Number) adapter.getItem(position);
                    mSnackbarLogic.make(view, "Number " + item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (vh instanceof CodeLableViewHolderFactory.ViewHolder) {
                    OrdinalLabel item = (OrdinalLabel) adapter.getItem(position);
                    String text = item.getLabel(getResources()) + "(" + item.getCode() + ")";
                    if (view.getId() == R.id.text_code) {
                        mSnackbarLogic.make(view, "The code of " + text + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                    } else if (view.getId() == R.id.text_label) {
                        mSnackbarLogic.make(view, "The label of " + text + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
            return false;
        }
    };

    SnackbarLogic mSnackbarLogic = new SnackbarLogic();
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_scramble_adapter_example);

        // find views
        mListView = (ListView) findViewById(R.id.list);

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
            ClassicScrambleAdapter<Object> adapter = new ClassicScrambleAdapter<>(this, items,
                    mListenerRelay,
                    new SimpleStringViewHolderFactory(),
                    new SimpleNumberViewHolderFactory(),
                    new CodeLableViewHolderFactory(getResources())
            );
            mListView.setAdapter(adapter);
        }
    }
}
