package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.BuzzViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.FizzBuzzViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.FizzViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.IntegerViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class FizzBuzzExampleActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                if (viewHolder instanceof FizzViewHolderFactory.ViewHolder) {
                    Integer item = (Integer) adapter.getItemAt(viewHolder.getAdapterPosition());
                    mSnackbarLogic.make(viewHolder.itemView, "Fizz " + item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof BuzzViewHolderFactory.ViewHolder) {
                    Integer item = (Integer) adapter.getItemAt(viewHolder.getAdapterPosition());
                    mSnackbarLogic.make(viewHolder.itemView, "Buzz " + item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof FizzBuzzViewHolderFactory.ViewHolder) {
                    Integer item = (Integer) adapter.getItemAt(viewHolder.getAdapterPosition());
                    mSnackbarLogic.make(viewHolder.itemView, "FizzBuzz " + item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof IntegerViewHolderFactory.ViewHolder) {
                    Integer item = (Integer) adapter.getItemAt(viewHolder.getAdapterPosition());
                    mSnackbarLogic.make(viewHolder.itemView, "Integer " + item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public boolean onLongClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                if (viewHolder instanceof FizzViewHolderFactory.ViewHolder) {
                    Integer item = (Integer) adapter.getItemAt(viewHolder.getAdapterPosition());
                    mSnackbarLogic.make(viewHolder.itemView, "Fizz " + item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof BuzzViewHolderFactory.ViewHolder) {
                    Integer item = (Integer) adapter.getItemAt(viewHolder.getAdapterPosition());
                    mSnackbarLogic.make(viewHolder.itemView, "Buzz " + item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof FizzBuzzViewHolderFactory.ViewHolder) {
                    Integer item = (Integer) adapter.getItemAt(viewHolder.getAdapterPosition());
                    mSnackbarLogic.make(viewHolder.itemView, "FizzBuzz " + item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (viewHolder instanceof IntegerViewHolderFactory.ViewHolder) {
                    Integer item = (Integer) adapter.getItemAt(viewHolder.getAdapterPosition());
                    mSnackbarLogic.make(viewHolder.itemView, "Integer " + item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        }
    };

    SnackbarLogic mSnackbarLogic = new SnackbarLogic();

    RecyclerView mRecyclerView;
    ScrambleAdapter<Integer> mAdapter;
    CheckBox mFizzCheck;
    CheckBox mBuzzCheck;

    FizzViewHolderFactory mFizzViewHolderFactory = new FizzViewHolderFactory();
    BuzzViewHolderFactory mBuzzViewHolderFactory = new BuzzViewHolderFactory();
    FizzBuzzViewHolderFactory mFizzBuzzViewHolderFactory = new FizzBuzzViewHolderFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fizz_buzz_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mFizzCheck = (CheckBox) findViewById(R.id.check_fizz);
        mBuzzCheck = (CheckBox) findViewById(R.id.check_buzz);

        // bind event handler
        mFizzCheck.setOnCheckedChangeListener(this);
        mBuzzCheck.setOnCheckedChangeListener(this);

        { // set adapter
            List<Integer> items = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                items.add(i);
            }
            mAdapter = new ScrambleAdapter<>(this, items,
                    mListenerRelay,
                    mFizzBuzzViewHolderFactory,
                    mBuzzViewHolderFactory,
                    mFizzViewHolderFactory,
                    new IntegerViewHolderFactory()
            );
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.check_fizz:
            case R.id.check_buzz: {
                refreshViewHolderFactory();
                break;
            }
            default: {
                // ignore
                break;
            }
        }
    }

    private void refreshViewHolderFactory() {
        List<Integer> items = mAdapter.getItems();
        if (mFizzViewHolderFactory.isEnabled() != mFizzCheck.isChecked()) {
            mFizzViewHolderFactory.setEnabled(mFizzCheck.isChecked());
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) % 3 == 0) {
                    mAdapter.notifyItemChanged(i);
                }
            }
        }
        if (mBuzzViewHolderFactory.isEnabled() != mBuzzCheck.isChecked()) {
            mBuzzViewHolderFactory.setEnabled(mBuzzCheck.isChecked());
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) % 5 == 0) {
                    mAdapter.notifyItemChanged(i);
                }
            }
        }
        mFizzBuzzViewHolderFactory.setEnabled(mFizzCheck.isChecked() && mBuzzCheck.isChecked());
    }
}
