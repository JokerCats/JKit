package net.jkcat.jkit.java.list;

import net.jkcat.core.vm.BaseViewModel;

/**
 * @author JokerCats on 2021.09.06
 */
class ActionListViewModel extends BaseViewModel<ActionListResult> {

    public ActionListViewModel() {
        model = new ActionListModel().register(this);
        model.getCacheDataAndRequest();
    }

}
