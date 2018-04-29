package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //데이터 준비
        items = new ArrayList<String>();
        items.add("Sunday");
        items.add("Monday");
        items.add("Tuesday");
        items.add("Wednesday");
        items.add("Thursday");
        items.add("Friday");
        items.add("Saturday");

        // 어댑터 생성
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_multiple_choice, items);

        // 어댑터 설정
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        //listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // 하나의 항목만 선택할 수 있도록 설정
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // 여러 항목을 선택할 수 있도록 설정

    }

    /**
     * ADD, DELETE 버튼 클릭 시 실행되는 메소드
     */
    public void mOnClick(View v) {
        EditText ed = (EditText) findViewById(R.id.newitem);
        switch (v.getId()) {
            case R.id.btnAdd:                                 // ADD 버튼 클릭시
                String text = ed.getText().toString();          // EditText에 입력된 문자열값을 얻기
                if (!text.isEmpty()) {                          // 입력된 text 문자열이 비어있지 않으면
                    items.add(text);                           // items 리스트에 입력된 문자열 추가
                    ed.setText("");                             // EditText 입력란 초기화
                    adapter.notifyDataSetChanged();            // 리스트 목록 갱신
                }
                break;
            case R.id.btnDelete:                             // DELETE 버튼 클릭시
               /* int pos = listView.getCheckedItemPosition(); // 현재 선택된 항목의 첨자(위치값) 얻기
                if (pos != ListView.INVALID_POSITION) {     // 선택된 항목이 있으면
                    items.remove(pos);                        // items 리스트에서 해당 위치의 요소 제거
                    listView.clearChoices();                  // 선택 해제
                    adapter.notifyDataSetChanged();
                    // 어답터와 연결된 원본데이터의 값이 변경된을 알려 리스트뷰 목록 갱신
                }
                break;
                */

                SparseBooleanArray sbArray = listView.getCheckedItemPositions();
                // 선택된 아이템의 위치를 알려주는 배열 ex) {0=true, 3=true, 4=false, 6=true}
                Log.d("MainActivity", sbArray.toString());

                if(sbArray.size() != 0) {
                    for (int i = listView.getCount() -1; i>=0; i--) { // 목록의 역순으로 순회하면서 항목 제거
                        if (sbArray.get(i)) {
                            items.remove(i);
                        }
                    }
                    listView.clearChoices();
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /**
     * 각 목록 버튼 클릭 시 실행되는 메소드
     */
    public void getList(View v) {
        switch(v.getId()) {
            case R.id.btnWeekList: // 요일목록
                // 기존 items 리스트의 데이터를 초기화하고 새로 추가
                items.clear();
                items.add("Sunday");
                items.add("Monday");
                items.add("Tuesday");
                adapter.notifyDataSetChanged(); // 갱신

                break;
            case R.id.btnBookList: // 도서목록
                // 기존 items 리스트의 데이터를 초기화하고 새로 추가
                items.clear();
                items.add("자바의 정석");
                items.add("토비의 스프링");
                items.add("안드로이드 정석");
                adapter.notifyDataSetChanged(); //갱신
                break;
        }
    }
}
