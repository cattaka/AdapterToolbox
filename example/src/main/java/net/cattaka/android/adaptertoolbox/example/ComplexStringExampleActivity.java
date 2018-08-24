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
import net.cattaka.android.adaptertoolbox.example.adapter.ComplexStringAdapter;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class ComplexStringExampleActivity extends AppCompatActivity {

    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                String item = (String) adapter.getItemAt(viewHolder.getAdapterPosition());
                switch (view.getId()) {
                    case R.id.text: {
                        mSnackbarLogic.make(view, item + " is clicked.(Text)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.button_a: {
                        mSnackbarLogic.make(view, item + " is clicked.(A)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.button_b: {
                        mSnackbarLogic.make(view, item + " is clicked.(B)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }

        @Override
        public boolean onLongClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                String item = (String) adapter.getItemAt(viewHolder.getAdapterPosition());
                switch (view.getId()) {
                    case R.id.text: {
                        mSnackbarLogic.make(view, item + " is long clicked.(Text)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.button_a: {
                        mSnackbarLogic.make(view, item + " is long clicked.(A)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.button_b: {
                        mSnackbarLogic.make(view, item + " is long clicked.(B)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                }
                return true;
            }
            return false;
        }
    };

    RecyclerView mRecyclerView;

    SnackbarLogic mSnackbarLogic = new SnackbarLogic();

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
            ComplexStringAdapter adapter = new ComplexStringAdapter(this, items, mListenerRelay);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }
    }
}
