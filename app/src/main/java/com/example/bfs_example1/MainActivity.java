package com.example.bfs_example1;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    Button btn_start;
    EditText edt_multi;
    TextView txt_result;

    HashMap map = new HashMap<String, HashMap<String, Integer>>();
    HashMap costs = new HashMap<String, Integer>();
    HashMap parents = new HashMap<String, String>();
    List<String> searched = new ArrayList<String>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap innerMap = new HashMap<String, Integer>() {
            {
                put("a", 6);
                put("b", 2);
            }
        };

        map.put("start", innerMap);
        innerMap = new HashMap<String, Integer>() {
            {
                put("fin", 1);
            }
        };
        map.put("a", innerMap);
        innerMap = new HashMap<String, Integer>() {
            {
                put("a", 3);
                put("fin", 5);
            }
        };
        map.put("b", innerMap);
        map.put("fin", null);

        costs.put("a", 6);
        costs.put("b", 2);
        costs.put("fin", Integer.MAX_VALUE);

        parents.put("a", "start");
        parents.put("b", "start");
        parents.put("in", null);

        btn_start = (Button) findViewById(R.id.btn_start);
        edt_multi = (EditText) findViewById(R.id.edt_multi);
        txt_result = (TextView) findViewById(R.id.txt_result);

        String key;
        HashMap<String, Integer> value;

        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();

        while (it.hasNext()) {
            key = (String)it.next().getKey();
            value =  (HashMap<String, Integer>) map.get(key);
            edt_multi.setText(edt_multi.getText() + "\n" + key + ": " + PrintMap(value));
        }

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeyksrtaSearch("me");
            }
        });
    }
    public String PrintMap(HashMap<String, Integer> map)
    {
        if (map == null)
            return "Нет соседей";
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        String result = "-> ";
        String key;
        Integer value;
        while (it.hasNext()) {
            key = (String)it.next().getKey();
            value =  (Integer) map.get(key);
            result += key + " = " + value.toString() + ", ";
        }
        return result;
    }

    public void DeyksrtaSearch(String key)
    {
       String node = FindLowestNode(costs);

       while (node != null)
       {
           HashMap<String, Integer> neighbors = (HashMap<String, Integer>) map.get(node);
           Integer cost = (Integer) costs.get(node);
           Integer new_cost;
            if (neighbors == null)
            {
                searched.add(node);
                node = FindLowestNode(costs);
                continue;
            }
           Iterator<Map.Entry<String, Integer>> it = neighbors.entrySet().iterator();

           while(it.hasNext())
           {
               Map.Entry<String, Integer> item = it.next();
               new_cost = cost + item.getValue();
               if ((Integer)costs.get(item.getKey()) > new_cost)
               {
                   costs.put(item.getKey(), new_cost);
                   parents.put(item.getKey(), node);
               }
           }
           searched.add(node);
           node = FindLowestNode(costs);
       }
       txt_result.setText("Кротчайший путь = " + Integer.toString((Integer)costs.get("fin")));
       return;
    }

    public String FindLowestNode(HashMap<String, Integer> costs)
    {
       Integer lowest_cost = Integer.MAX_VALUE;
       String lowest_cost_node = null;
       Integer cost;

        Iterator<Map.Entry<String, Integer>> it = costs.entrySet().iterator();

        while(it.hasNext())
        {
            Map.Entry<String, Integer> item = it.next();
            cost = (Integer) item.getValue();
            if ((cost < lowest_cost)&& !(searched.contains(item.getKey())))
            {
                lowest_cost = cost;
                lowest_cost_node = item.getKey();
            }
        }
        return lowest_cost_node;
    }
    /*public String Search(String key)
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
        for (String item: (String[]) map.get(key))
        {
            queue.add(item);
        }
    }*/
}
