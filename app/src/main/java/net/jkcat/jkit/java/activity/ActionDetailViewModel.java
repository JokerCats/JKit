package net.jkcat.jkit.java.activity;

import net.jkcat.core.vm.BaseViewModel;

/**
 * @author JokerCats on 2021.09.06
 */
public class ActionDetailViewModel extends BaseViewModel<ActionDetailResult> {

    public ActionDetailViewModel() {
        model = new ActionDetailModel().register(this);
        model.getCacheDataAndRequest();
    }

}
