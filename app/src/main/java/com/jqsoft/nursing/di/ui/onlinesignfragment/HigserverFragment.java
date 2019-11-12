//package com.jqsoft.grassroots_civil_administration_platform.di.ui.onlinesignfragment;
//import android.content.Intent;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.jqsoft.nursing.R;
//import com.jqsoft.nursing.base.Constants;
//import com.jqsoft.nursing.bean.SignSeverPakesBeanList;
//import com.jqsoft.nursing.di.ui.activity.DoctorServerDetails;
//import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
//import com.jqsoft.nursing.di.ui.onlinesignadapter.SignDoctorServerAdapter;
//import com.jqsoft.nursing.di.ui.onlinesignadapter.SignDoctorServersAdapter;
//
//import java.util.ArrayList;
//
//import butterknife.BindView;
//
//public class HigserverFragment extends AbstractFragment  {
//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerView;
//    private ArrayList<SignSeverPakesBeanList> datalist = new ArrayList<>();
//    private SignDoctorServerAdapter serverAdapter;
//    @Override
//    protected void loadData() {
//
//        serverAdapter = new SignDoctorServerAdapter(getActivity(), datalist);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        serverAdapter.setFragmentInteraction((SignDoctorServerAdapter.FragmentInteraction) getActivity());
//        recyclerView.setAdapter(serverAdapter);//
//        serverAdapter.notifyDataSetChanged();
//        serverAdapter.setOnItemClickListener(new SignDoctorServersAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClick(View view, int position) {
//                Intent intent = new Intent(getActivity(), DoctorServerDetails.class);
//                intent.putExtra(Constants.SEVER_KEY,datalist.get(position).getKey());
//                startActivity(intent);
//            }
//
//            @Override
//            public void OnItemLongClick(View view, int position) {
//
//            }
//        });
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.highseverdoctorfragment;
//    }
//
//    @Override
//    protected void initData() {
//    }
//    public ArrayList<SignSeverPakesBeanList> setDatalist(ArrayList<SignSeverPakesBeanList> list) {
//        datalist.addAll(list);
//        serverAdapter.setNewData(datalist);
//       // serverAdapter.notifyDataSetChanged();
//        return datalist;
//    }
//
//    @Override
//    protected void initView() {
//
//    }
//
//
//    @Override
//    protected void initInject() {
//
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        serverAdapter.unregisterDeleteAction();
//    }
//
//}
