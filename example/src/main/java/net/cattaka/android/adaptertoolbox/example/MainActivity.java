package net.cattaka.android.adaptertoolbox.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.ActivityEntryAdapter;
import net.cattaka.android.adaptertoolbox.example.data.ActivityEntry;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final List<ActivityEntry> ACTIVITY_ENTRIES = Arrays.asList(
            new ActivityEntry("Enum with Spinner", null,
                    new ActivityEntry("CodeLabelAdapter", CodeLabelExampleActivity.class)
            ),
            new ActivityEntry("MergeRecyclerAdapter", null,
                    new ActivityEntry("Simple String", SimpleStringExampleActivity.class),
                    new ActivityEntry("Multi event handling", ComplexStringExampleActivity.class),
                    new ActivityEntry("Header and footer", RecyclerViewHeaderExampleActivity.class),
                    new ActivityEntry("Sectioned list by multi adapters", MultiAdapterExampleActivity.class)
            ),
            new ActivityEntry("ScrambleAdapter", null,
                    new ActivityEntry("Multi types in List<Object>", ScrambleAdapterExampleActivity.class),
                    new ActivityEntry("Manipulable list", ManipulableListExampleActivity.class),
                    new ActivityEntry("Nested ScrambleAdapter", NestedScrambleAdapterExampleActivity.class),
                    new ActivityEntry("Sectioned list by List<Object>", MultiAdapterExampleActivity.class)
            ),
            new ActivityEntry("Tree", null,
                    new ActivityEntry("Simple tree", TreeItemAdapterExampleActivity.class),
                    new ActivityEntry("Selectable tree", ChoosableTreeItemAdapterExampleActivity.class)
            ),
            new ActivityEntry("Classic AdapterView", null,
                    new ActivityEntry("ClassicScrambleAdapter with ListView", ClassicScrambleAdapterExampleActivity.class),
                    new ActivityEntry("ClassicScrambleAdapter with Spinner", SpinnerScrambleAdapterExampleActivity.class),
                    new ActivityEntry("AbsTreeItemAdapter with Spinner", SpinnerTreeItemAdapterExampleActivity.class)
            )
    );

    ListenerRelay<ActivityEntryAdapter, ActivityEntryAdapter.ViewHolder> mListenerRelay = new ListenerRelay<ActivityEntryAdapter, ActivityEntryAdapter.ViewHolder>() {
        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull ActivityEntryAdapter adapter, @NonNull ActivityEntryAdapter.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                ActivityEntry entry = adapter.getItemAt(viewHolder.getAdapterPosition()).getItem();
                if (entry != null && entry.getClazz() != null) {
                    Intent intent = new Intent(MainActivity.this, entry.getClazz());
                    startActivity(intent);
                }
            }
        }
    };

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        ActivityEntryAdapter adapter = new ActivityEntryAdapter(this, ACTIVITY_ENTRIES);
        adapter.setListenerRelay(mListenerRelay);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
