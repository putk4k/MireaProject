package ru.mirea.vikulov.mireaproject.ui.map;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import ru.mirea.vikulov.mireaproject.R;
import ru.mirea.vikulov.mireaproject.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private MapView mapView = null;
    private Context context;
    boolean isWork;
    private static final int REQUEST_CODE_PERMISSION = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        context = inflater.getContext();
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = binding.mapView;
        mapView.setZoomRounding(true);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(55.794229, 37.700772);
        mapController.setCenter(startPoint);
        MyLocationNewOverlay locationNewOverlay = new MyLocationNewOverlay(new
                GpsMyLocationProvider(context),mapView);
        locationNewOverlay.enableMyLocation();
        mapView.getOverlays().add(locationNewOverlay);
        CompassOverlay compassOverlay = new CompassOverlay(context, new
                InternalCompassOrientationProvider(context), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mapView.getOverlays().add(scaleBarOverlay);

        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(55.794229, 37.700772));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(context,"Кузница величайших умов \n" +
                                "Адресс: ул. Стромынка, 20",
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker);

        marker.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));

        marker.setTitle("Title");

        Marker marker1 = new Marker(mapView);
        marker1.setPosition(new GeoPoint(55.664182, 37.481118));

        marker1.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker1, MapView mapView) {
                Toast.makeText(context,"Заведение высокой кухни \n" +
                                "Адресс: ул. Покрышкина, 2",
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker1);

        marker1.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.mcd, null));

        Marker marker2 = new Marker(mapView);
        marker2.setPosition(new GeoPoint(55.720418, 37.378791));
        marker2.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker2, MapView mapView) {
                Toast.makeText(context,"Богодельня для отдыха души и тела \n" +
                                "Адресс: Хорошёвский пр., 14",
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker2);

        marker2.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.shop, null));

        Marker marker3 = new Marker(mapView);
        marker3.setPosition(new GeoPoint(55.748473, 37.588817));
        marker3.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker3, MapView mapView) {
                Toast.makeText(context,"Лучший мармелад в Москве \n" +
                                "Адресс: ул. Арбат, 43",
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker3);

        marker3.setIcon(ResourcesCompat.getDrawable(getResources(), android.R.drawable.star_big_off, null));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(context,
                PreferenceManager.getDefaultSharedPreferences(context));
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Configuration.getInstance().save(context,

                PreferenceManager.getDefaultSharedPreferences(context));

        if (mapView != null) {
            mapView.onPause();
        }
    }
}