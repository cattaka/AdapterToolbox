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
            new ActivityEntry(R.string.activity_entry_divider, null,
                    new ActivityEntry(R.string.activity_entry_vertical_list_divider, VerticalListDividerExampleActivity.class)
            ),
            new ActivityEntry(R.string.activity_entry_enum_with_spinner, null,
                    new ActivityEntry(R.string.activity_entry_code_label_adapter, CodeLabelExampleActivity.class)
            ),
            new ActivityEntry(R.string.activity_entry_merge_recycler_adapter, null,
                    new ActivityEntry(R.string.activity_entry_simple_string, SimpleStringExampleActivity.class),
                    new ActivityEntry(R.string.activity_entry_multi_event_handling, ComplexStringExampleActivity.class),
                    new ActivityEntry(R.string.activity_entry_header_and_footer, RecyclerViewHeaderExampleActivity.class),
                    new ActivityEntry(R.string.activity_entry_sectined_list_by_multi_adapters, MultiAdapterExampleActivity.class)
            ),
            new ActivityEntry(R.string.activity_entry_scramble_adapter, null,
                    new ActivityEntry(R.string.activity_entry_muluti_types_in_list_object, ScrambleAdapterExampleActivity.class),
                    new ActivityEntry(R.string.activity_entry_manipulatable_list, ManipulableListExampleActivity.class),
                    new ActivityEntry(R.string.activity_entry_nested_scramble_adapter, NestedScrambleAdapterExampleActivity.class),
                    new ActivityEntry(R.string.activity_entry_sectioned_list_by_list_object, MultiAdapterExampleActivity.class),
                    new ActivityEntry(R.string.activity_entry_fizz_buzz, FizzBuzzExampleActivity.class)
            ),
            new ActivityEntry(R.string.activity_entry_data_binding, null,
                    new ActivityEntry(R.string.activity_entry_manipulatable_list_with_data_binding, DataBindingManipulableListExampleActivity.class)
            ),
            new ActivityEntry(R.string.activity_entry_tree, null,
                    new ActivityEntry(R.string.activity_entry_simple_tree, TreeItemAdapterExampleActivity.class),
                    new ActivityEntry(R.string.activity_entry_single_choosable_tree, SingleChoosableTreeItemAdapterExampleActivity.class),
                    new ActivityEntry(R.string.activity_entry_multi_choosable_tree, MultiChoosableTreeItemAdapterExampleActivity.class)
            ),
            new ActivityEntry(R.string.activity_entry_classic_adapter_view, null,
                    new ActivityEntry(R.string.activity_entry_classic_scramble_adapter_with_list_view, ClassicScrambleAdapterExampleActivity.class),
                    new ActivityEntry(R.string.activity_entry_classic_scramble_adapter_with_spinner, SpinnerScrambleAdapterExampleActivity.class),
                    new ActivityEntry(R.string.activity_entry_abs_tree_item_adapter_with_spinner, SpinnerTreeItemAdapterExampleActivity.class)
            )
    );

    ListenerRelay<ActivityEntryAdapter, ActivityEntryAdapter.ViewHolder> mListenerRelay = new ListenerRelay<ActivityEntryAdapter, ActivityEntryAdapter.ViewHolder>() {
        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull ActivityEntryAdapter adapter, @NonNull ActivityEntryAdapter.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                ActivityEntryAdapter.WrappedItem wrappedItem = adapter.getItemAt(viewHolder.getAdapterPosition());
                ActivityEntry entry = wrappedItem.getItem();
                if (entry != null && entry.getClazz() != null) {
                    Intent intent = new Intent(MainActivity.this, entry.getClazz());
                    startActivity(intent);
                } else {
                    adapter.doOpen(wrappedItem, !wrappedItem.isOpened());
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
