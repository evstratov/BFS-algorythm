package com.example.bfs_example1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    Button btn_start;
    EditText edt_multi;
    TextView txt_result;

    Hashtable ht = new Hashtable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ht.put("me", new String[] {"bob","alice","claire"});
        ht.put("bob", new String[] {"anuj","peggy"});
        ht.put("alice", new String[] {"peggy"});
        ht.put("claire", new String[] {"thom", "jonny"});
        ht.put("anuj", new String[] {});
        ht.put("peggy", new String[] {});
        ht.put("thom", new String[] {});
        ht.put("jonny", new String[] {});

        btn_start = (Button) findViewById(R.id.btn_start);
        edt_multi = (EditText) findViewById(R.id.edt_multi);
        txt_result = (TextView) findViewById(R.id.txt_result);

        Enumeration enm = ht.keys();
        String key;
        String[] value;

        while (enm.hasMoreElements()) {
            key = (String)enm.nextElement();
            value = (String[]) ht.get(key);
            edt_multi.setText(edt_multi.getText() + "\n" + key + ": " + Arrays.toString(value));
        }

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search("me");
            }
        });
    }
    public String Search(String key)
    {
        List<String> searched = new ArrayList<String>();;
        Queue<String> queue = new LinkedList<>();
        QueueAdd(queue, key);
        String person;


        while(queue.size() > 0)
        {
            person = (String) queue.poll();

            if(!searched.contains(person))
            {
                if (IsSeller(person)) {
                    txt_result.setText("Продавец манго: " + person);
                    return person;
                } else {
                    searched.add(person);
                    QueueAdd(queue, person);
                }
            }
        }
       return "";
    }

    public boolean IsSeller(String person)
    {
        if(person.contains("g"))
            return true;
        else
            return  false;
    }
    public void QueueAdd(Queue<String> queue, String key)
    {
        for (String item: (String[]) ht.get(key))
        {
            queue.add(item);
        }
    }
}
