package com.example.androidapp.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CarbonRepository {

    private CarbonDao carbonDao;
    private static CarbonRepository instance;
    private LiveData<List<CarbonEmissions >> allEmissions;

    private CarbonRepository(Application application){
        CarbonDatabase database = CarbonDatabase.getInstance(application);
        carbonDao = database.noteDao();
        allEmissions = carbonDao.getAllEmissions();    }

    public static synchronized CarbonRepository getInstance(Application application){
        if(instance == null)
            instance = new CarbonRepository(application);

        return instance;
    }

    public LiveData<List<CarbonEmissions>> getAllEmissions(){
        return allEmissions;
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