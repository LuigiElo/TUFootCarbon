package com.example.androidapp.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CarbonRepository {

    private CarbonDao carbonDao;
    private static CarbonRepository instance;
    private LiveData<List<CarbonEmissions >> allEmissions;
    private LiveData<List<CarbonEmissions >> typeEmissions;
    private LiveData<Float> total;

    private CarbonRepository(Application application){
        final CarbonDatabase database = CarbonDatabase.getInstance(application);
        carbonDao = database.noteDao();
        allEmissions = carbonDao.getAllEmissions();
    }

    public static synchronized CarbonRepository getInstance(Application application){
        if(instance == null)
            instance = new CarbonRepository(application);

        return instance;
    }

    public LiveData<List<CarbonEmissions>> getAllEmissions(){

        return allEmissions;
    }


    public LiveData<Float> getEmissionTotal(){
        total = carbonDao.getEmissionTotal( FirebaseAuth.getInstance().getCurrentUser().getEmail());
        return total;
    }


    public LiveData<List<CarbonEmissions>> getAllEmissionsByType(String type){
        typeEmissions = carbonDao.getAllEmissionsByType(type, FirebaseAuth.getInstance().getCurrentUser().getEmail());
        return typeEmissions;
    }

    public void insert(CarbonEmissions carbonEmissions) {
        new InsertCarbonEmissionAsync(carbonDao).execute(carbonEmissions);
    }

    public void delete(CarbonEmissions carbonEmissions) {
        new DeleteCarbonEmissionAsync(carbonDao).execute(carbonEmissions);
}

    public void deleteAllNotes(){
        new DeleteAllNotesAsync(carbonDao).execute();
    }






    private static class InsertCarbonEmissionAsync extends AsyncTask<CarbonEmissions,Void,Void> {
        private CarbonDao carbonDao;

        private InsertCarbonEmissionAsync(CarbonDao carbonDao) {
            this.carbonDao = carbonDao;
        }

        @Override
        protected Void doInBackground(CarbonEmissions... carbonEmissions) {
            carbonDao.insert(carbonEmissions[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsync extends AsyncTask<Void,Void,Void> {
        private CarbonDao carbonDao;

        private DeleteAllNotesAsync(CarbonDao carbonDao) {
            this.carbonDao = carbonDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            carbonDao.deleteAll();
            return null;
        }
    }

    private static class DeleteCarbonEmissionAsync extends AsyncTask<CarbonEmissions,Void,Void> {
        private CarbonDao carbonDao;

        private DeleteCarbonEmissionAsync(CarbonDao carbonDao) {
            this.carbonDao = carbonDao;
        }

        @Override
        protected Void doInBackground(CarbonEmissions... carbonEmissions) {
            carbonDao.delete(carbonEmissions[0]);
            return null;
        }
    }
}
