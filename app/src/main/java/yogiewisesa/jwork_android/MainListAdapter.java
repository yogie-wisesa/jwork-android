/**
 * @author Yogie Wisesa
 * @version 26/6/21
 * 
 * class main list adapter
 * untuk menghandle expandable list pada main activity
 */
package yogiewisesa.jwork_android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MainListAdapter extends BaseExpandableListAdapter{
    private Context _context;
    private ArrayList<Recruiter> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Recruiter, ArrayList<Job>> _listDataChild;

    /**
     * constructor main list adapter
     * @param context
     * @param listDataHeader
     * @param listChildData
     */
    public MainListAdapter(Context context, ArrayList<Recruiter> listDataHeader,
                           HashMap<Recruiter, ArrayList<Job>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    /**
     * method getter child/job
     * @param groupPosition
     * @param childPosititon
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    /**
     * method getter child id
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * getter child view
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Job childText = (Job) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_job, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);

        txtListChild.setText(childText.getName());
        return convertView;
    }

    /**
     * getter jumlah child
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    /**
     * method getter posisi grup
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    /**
     * method getter jumlah group
     * @return
     */
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    /**
     * method getter id group
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * method getter view dari group
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final Recruiter headerTitle = (Recruiter) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_recruiter, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getName());

        return convertView;
    }

    /**
     * method untuk cek apakah id stable
     * @return false
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * method untuk cek apakah child dapat dipilih
     * @param groupPosition
     * @param childPosition
     * @return true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
