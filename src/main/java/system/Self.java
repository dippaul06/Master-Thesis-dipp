package system;

import utils.MainUtils;

// ------------------------------------------------------------
// SELF
// ------------------------------------------------------------
public interface Self<T extends Self<T>> {

    default T self() { return MainUtils.TCast.cast(this); }
}
