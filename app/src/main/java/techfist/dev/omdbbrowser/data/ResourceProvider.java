package techfist.dev.omdbbrowser.data;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;


/**
 * a resource provider class to provide system resources to different component.
 * as requirement other get resource API's can be added here.
 *
 * agenda for this class is remove any context related dependencies from ViewModels
 * <p>
 * as of now am only writing api to provide string
 * <p>
 */

public class ResourceProvider {

    private Context context;
    private Resources resources;

    public ResourceProvider(Context context) {
        this.context = context;
        this.resources = context.getResources();
    }


    public String getString(@StringRes final int id) {
        return resources.getString(id);
    }


    public String getString(@StringRes final int id, final Object... args) {
        return resources.getString(id, args);
    }

    public String getPlurals(@PluralsRes final int id, final int quantity, Object... formattting) {
        return resources.getQuantityString(id, quantity, formattting);

    }

    public String getPlurals(@PluralsRes final int id, final int quantity) {
        return resources.getQuantityString(id, quantity);

    }
}
