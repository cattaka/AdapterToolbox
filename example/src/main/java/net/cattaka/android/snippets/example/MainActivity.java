package net.cattaka.android.snippets.example;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.cattaka.android.snippets.adapter.CodeLabelAdapter;
import net.cattaka.android.snippets.adapter.ICodeLabel;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find views
        mList = (ListView) findViewById(R.id.list);

        // bind event handlers
        mList.setOnItemClickListener(this);

        mList.setAdapter(CodeLabelAdapter.newInstance(this, Activities.values(), false));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.list) {
            Activities target = (Activities) parent.getItemAtPosition(position);
            Intent intent = new Intent(this, target.clazz);
            startActivity(intent);
        }
    }

    public enum Activities implements ICodeLabel {
        CodeLabelExample(CodeLabelExampleActivity.class),
        SimpleStringExample(SimpleStringExampleActivity.class),
        RecyclerViewHeaderExample(RecyclerViewHeaderExampleActivity.class),
        MultiAdapterExample(MultiAdapterExampleActivity.class),
        ComplexStringExample(ComplexStringExampleActivity.class),
        ScrambleAdapterExample(ScrambleAdapterExampleActivity.class),
        OperatableListExample(OperatableListExampleActivity.class),
        NestedScrambleAdapterExample(NestedScrambleAdapterExampleActivity.class),
        //
        ;
        public final Class<? extends Activity> clazz;

        Activities(Class<? extends Activity> clazz) {
            this.clazz = clazz;
        }

        @Override
        public String getCode() {
            return name();
        }

        @Override
        public String getLabel(Resources res) {
            return name();
        }
    }
}
