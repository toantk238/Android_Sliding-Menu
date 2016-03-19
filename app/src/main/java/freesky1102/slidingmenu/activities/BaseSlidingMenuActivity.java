package freesky1102.slidingmenu.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import freesky1102.slidingmenu.R;

/**
 * This class is created to support making the Activity with one or two tabs
 * <p>
 * Created by FreeSky1102 on 3/1/16.
 */
public class BaseSlidingMenuActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private View mLeftDrawer;
    private View mRightDrawer;
    private View mContentView;

    public static final int LEFT_DRAWER = 1;
    public static final int RIGHT_DRAWER = 2;

    @IntDef({LEFT_DRAWER, RIGHT_DRAWER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DRAWER_MODE {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu);
        findViews();
        initDrawer();

        inflateFragment(R.id.left_drawer, makeFirstLeftDrawerFragment());
        inflateFragment(R.id.right_drawer, makeFirstRightDrawerFragment());
    }

    private void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftDrawer = findViewById(R.id.left_drawer);
        mRightDrawer = findViewById(R.id.right_drawer);
        mContentView = findViewById(R.id.content_layout);
        // Just ignore a bug view after the activity re-created
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerLayout.closeDrawers();

            }
        });
    }

    private void initDrawer() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
                mActionBarDrawerToggle.syncState();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
                mActionBarDrawerToggle.syncState();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (drawerView == mRightDrawer)
                    mContentView.setTranslationX(slideOffset * drawerView.getWidth() * (-1.0f));
                else if (drawerView == mLeftDrawer)
                    mContentView.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    }

    @Override
    public void onBackPressed() {
        if (isAnyDrawerOpened()) {
            closeAllDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDrawerLayout.removeDrawerListener(mActionBarDrawerToggle);
    }

    private boolean hasFragment(@IdRes int idRes) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment inflatedFragment = fragmentManager.findFragmentById(idRes);
        return inflatedFragment != null;
    }

    /**
     * @return the Fragment will be displayed as the left drawer during the first loading time of this activity
     */
    protected Fragment makeFirstLeftDrawerFragment() {
        return null;
    }

    /**
     * @return the Fragment will be displayed as the right drawer during the first loading time of this activity
     */
    protected Fragment makeFirstRightDrawerFragment() {
        return null;
    }

    /**
     * Replace fragment into layoutRes without adding to back stack
     */
    protected final void inflateFragment(@IdRes int layoutRes, Fragment fragment) {
        if (!hasFragment(layoutRes) && fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(layoutRes, fragment).commit();
        }
    }

    public final boolean isAnyDrawerOpened() {
        return isDrawerOpened(LEFT_DRAWER) || isDrawerOpened(RIGHT_DRAWER);
    }

    /**
     * check if menu in @param drawerMode is open
     *
     * @param drawerMode {@Link SlidingMenuActivity#LEFT_DRAWER} or {@Link SlidingMenuActivity#RIGHT_DRAWER}
     * @return
     */
    public final boolean isDrawerOpened(@DRAWER_MODE int drawerMode) {
        if (mDrawerLayout == null) return false;

        if (drawerMode == LEFT_DRAWER)
            return mDrawerLayout.isDrawerOpen(GravityCompat.START);
        else if (drawerMode == RIGHT_DRAWER)
            return mDrawerLayout.isDrawerOpen(GravityCompat.END);
        return false;
    }

    public final void openDrawer(@DRAWER_MODE int drawerMode) {
        if (mDrawerLayout == null) return;

        if (drawerMode == LEFT_DRAWER)
            mDrawerLayout.openDrawer(GravityCompat.START);
        else if (drawerMode == RIGHT_DRAWER)
            mDrawerLayout.openDrawer(GravityCompat.END);
    }

    public final void closeAllDrawer() {
        if (mDrawerLayout == null) return;

        mDrawerLayout.closeDrawers();
    }

    public final void closeDrawer(@DRAWER_MODE int drawerMode) {
        if (mDrawerLayout == null) return;

        if (drawerMode == LEFT_DRAWER)
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else if (drawerMode == RIGHT_DRAWER)
            mDrawerLayout.closeDrawer(GravityCompat.END);
    }

    public final void lockAllDrawer() {
        if (mDrawerLayout == null) return;

        lockDrawer(LEFT_DRAWER);
        lockDrawer(RIGHT_DRAWER);
    }

    public final void lockDrawer(@DRAWER_MODE int drawerMode) {
        if (mDrawerLayout == null) return;

        int gravity = GravityCompat.START;
        if (drawerMode == LEFT_DRAWER) {
            gravity = GravityCompat.START;
        } else if (drawerMode == RIGHT_DRAWER) {
            gravity = GravityCompat.END;
        }

        if (mDrawerLayout.getDrawerLockMode(gravity) == DrawerLayout.LOCK_MODE_UNLOCKED) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, gravity);
        }
    }

    public final void unlockAllDrawer() {
        if (mDrawerLayout == null) return;

        unlockDrawer(LEFT_DRAWER);
        unlockDrawer(RIGHT_DRAWER);
    }

    public final void unlockDrawer(@DRAWER_MODE int drawerMode) {
        if (mDrawerLayout == null) return;
        int gravity = GravityCompat.START;
        if (drawerMode == LEFT_DRAWER) {
            gravity = GravityCompat.START;
        } else if (drawerMode == RIGHT_DRAWER) {
            gravity = GravityCompat.END;
        }

        if (mDrawerLayout.getDrawerLockMode(gravity) == DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, gravity);
        }
    }

    public final Fragment getDrawerFragment(@DRAWER_MODE int drawerMode) {

        int fragment_res_id = 0;
        if (drawerMode == LEFT_DRAWER) {
            fragment_res_id = R.id.left_drawer;
        } else if (drawerMode == RIGHT_DRAWER) {
            fragment_res_id = R.id.right_drawer;
        }

        if (fragment_res_id == 0) return null;

        return getSupportFragmentManager().findFragmentById(fragment_res_id);
    }

}