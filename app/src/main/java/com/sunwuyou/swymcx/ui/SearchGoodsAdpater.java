package com.sunwuyou.swymcx.ui;


import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchGoodsAdpater extends BaseAdapter implements Filterable {
    private final Object mLock;
    SparseBooleanArray selected = new SparseBooleanArray();
    private Context context;
    private ArrayFilter filter;
    private List<GoodsThin> goods;
    private boolean isShowStock = true;
    private List<GoodsThin> tempGoods;
    private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean) {
            int i = Integer.parseInt(paramAnonymousCompoundButton.getTag().toString());
            if (paramAnonymousBoolean) {
                SearchGoodsAdpater.this.selected.put(i, true);
                return;
            }
            SearchGoodsAdpater.this.selected.put(i, false);
        }
    };
    private boolean isLoaded;
    private boolean isUseFull;

    public SearchGoodsAdpater(Context context, boolean isShowStock) {
        super();
        this.mLock = new Object();
        this.selected = new SparseBooleanArray();
        this.isShowStock = true;
        this.context = context;
        this.isShowStock = isShowStock;
    }

    public SearchGoodsAdpater(Context arg2) {
        super();
        this.mLock = new Object();
        this.selected = new SparseBooleanArray();
        this.isShowStock = true;
        this.context = arg2;
    }

    public void setGoods(List<GoodsThin> goods) {
        this.goods = goods;
    }

    @Override
    public int getCount() {
        if (this.tempGoods == null) {
            return 0;
        }
        return this.tempGoods.size();
    }

    @Override
    public GoodsThin getItem(int position) {
        return this.tempGoods.get(position);
    }

    // 获取已经选择 的 item
    public List<GoodsThin> getSelect() {
        List<GoodsThin> localArrayList = new ArrayList<GoodsThin>();
        if (selected.size() <= 0) {
            return localArrayList;
        }
        for (int i = 0; i < selected.size(); i++) {
            int j = this.selected.keyAt(i);
            if (this.selected.get(j)) {
                localArrayList.add(tempGoods.get(j));
            }
        }
        return localArrayList;
    }

    public List<GoodsThin> getTempGoods() {
        return this.tempGoods;
    }

    public void setTempGoods(List<GoodsThin> arg2) {
        this.tempGoods = arg2;
        this.selected.clear();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item v0;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.search_goods_item, null);
            v0 = new Item();
            v0.init(convertView);
            convertView.setTag(v0);
        } else {
            v0 = (Item) convertView.getTag();
        }
        v0.textView.setText(this.tempGoods.get(position).getName());
        if (this.isShowStock) {
            v0.tvStock.setVisibility(View.VISIBLE);
            TextView v2 = v0.tvStock;
            String v1 = TextUtils.isEmptyS(this.tempGoods.get(position).getBigstocknumber()) ? "无库存" : "(" + this.tempGoods.get(position).getBigstocknumber() + ")";
            v2.setText(v1);
        } else {
            v0.tvStock.setVisibility(View.GONE);
        }
        v0.checkBox.setOnCheckedChangeListener(this.changeListener);
        v0.checkBox.setTag(position);
        v0.checkBox.setChecked(this.selected.get(position));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (this.filter == null)
            this.filter = new ArrayFilter(null);
        return this.filter;
    }


    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public void setIsUseFull(boolean isUseFull) {
        this.isUseFull = isUseFull;
    }

    class Item {
        public CheckBox checkBox;
        public TextView textView;
        public TextView tvStock;

        public void init(View arg2) {
            this.textView = arg2.findViewById(R.id.textView);
            this.tvStock = arg2.findViewById(R.id.tvStock);
            this.checkBox = arg2.findViewById(R.id.checkBox);
        }
    }

    public class ArrayFilter extends Filter {


        private ArrayFilter(SearchGoodsAdpater arg2) {
            super();
            isLoaded = true;
            isUseFull = true;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<GoodsThin> localArrayList = null;
            Filter.FilterResults localFilterResults = new Filter.FilterResults();
            if ((constraint == null) || (constraint.length() == 0)) {
                synchronized (SearchGoodsAdpater.this.mLock) {
                    if (SearchGoodsAdpater.this.goods == null) {
                        localFilterResults.count = 0;
                        return localFilterResults;
                    }
                    localFilterResults.values = SearchGoodsAdpater.this.goods;
                    localFilterResults.count = SearchGoodsAdpater.this.goods.size();
                }
            }
            do {
                try {
                    Thread.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (!isLoaded);

            if (isUseFull) {
                localArrayList = SearchGoodsAdpater.this.goods;
            } else {
                localArrayList = SearchGoodsAdpater.this.tempGoods;
            }
            String[] arrayOfString;

            ArrayList<GoodsThin> arrayList = new ArrayList<GoodsThin>();
            System.out.println("i>>>" + localArrayList.size());
            for (int i = 0; i < localArrayList.size(); i++) {
                String localString2 = null;
                arrayOfString = Utils.GOODS_CHECK_SELECT.split(",");
                for (int k = 0; k < arrayOfString.length; k++) {
                    if (arrayOfString[k].equals("pinyin")) {
                        localString2 = ((GoodsThin) localArrayList.get(i)).getPinyin();
                    } else if (arrayOfString[k].equals("name")) {
                        localString2 = ((GoodsThin) localArrayList.get(i)).getName();
                    } else if (arrayOfString[k].equals("id")) {
                        localString2 = ((GoodsThin) localArrayList.get(i)).getId();
                    } else if (arrayOfString[k].equals("barcode")) {
                        localString2 = ((GoodsThin) localArrayList.get(i)).getBarcode();
                    }
                    if (TextUtils.isEmptyS(localString2)
                            && (localString2.toLowerCase(Locale.CHINA).contains(constraint))) {
                        arrayList.add(localArrayList.get(i));
                        break;
                    }
                }
            }
            localFilterResults.values = arrayList;
            localFilterResults.count = arrayList.size();
            return localFilterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
//            selected.clear();
//            tempGoods = (ArrayList<GoodsThin>) results.values;
//            if (results.count > 0) {
//                notifyDataSetChanged();
//                return;
//            }
//            notifyDataSetInvalidated();
            SearchGoodsAdpater.this.selected.clear();
            SearchGoodsAdpater.this.tempGoods = (ArrayList<GoodsThin>) results.values;
            if (results.count > 0) {
                SearchGoodsAdpater.this.notifyDataSetChanged();
            } else {
                SearchGoodsAdpater.this.notifyDataSetInvalidated();
            }
        }


    }
}