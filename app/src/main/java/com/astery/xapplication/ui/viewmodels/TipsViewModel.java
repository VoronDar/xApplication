package com.astery.xapplication.ui.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.astery.xapplication.R;
import com.astery.xapplication.data_source.remote.listeners.RemoteOneGettable;
import com.astery.xapplication.pojo.Event;
import com.astery.xapplication.pojo.EventTemplate;
import com.astery.xapplication.pojo.serialazable.EventDescription;
import com.astery.xapplication.repository.Repository;
import com.astery.xapplication.repository.listeners.GetItemListener;
import com.astery.xapplication.repository.listeners.JobListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class TipsViewModel extends ViewModel {

}
