package fi.jamk.golfcourseapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;


public class GolfAdapter extends RecyclerView.Adapter<GolfAdapter.ViewHolder>{

    public List<GolfData> golfcourses;
    public GolfAdapter(List<GolfData> golfcourses){
        this.golfcourses = golfcourses;
    }
    public int getItemCount(){
        return golfcourses.size();
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.golf_course, parent, false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(ViewHolder holder, int position){
        GolfData golfdata = golfcourses.get(position);
        new DownloadImageTask(holder.golfImage).execute("http://ptm.fi/jamk/android/golfcourses/" + golfdata.image);
        holder.golfName.setText(golfdata.name);
        holder.golfAddress.setText(golfdata.address);
        holder.golfPhone.setText(golfdata.phonenr);
        holder.golfEmail.setText(golfdata.email);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
            public ImageView golfImage;
            public TextView golfName;
            public TextView golfAddress;
            public TextView golfPhone;
            public TextView golfEmail;

            public ViewHolder(View itemView){
               super(itemView);
               golfImage = (ImageView) itemView.findViewById(R.id.golfImage);
               golfName = (TextView) itemView.findViewById(R.id.golfName);
               golfAddress = (TextView) itemView.findViewById(R.id.golfAddress);
               golfPhone = (TextView) itemView.findViewById(R.id.golfPhone);
               golfEmail = (TextView) itemView.findViewById(R.id.golfEmail);
        }
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{
        ImageView imagev;
        public DownloadImageTask(ImageView image) {
            this.imagev = image;
        }
        protected Bitmap doInBackground(String... urls){
            String url = urls[0];
            Bitmap icon = null;
            try{
                InputStream instream = new java.net.URL(url).openStream();
                icon = BitmapFactory.decodeStream(instream);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return icon;
        }
        protected void onPostExecute(Bitmap bitmap) {
            imagev.setImageBitmap(bitmap);
        }
    }
}