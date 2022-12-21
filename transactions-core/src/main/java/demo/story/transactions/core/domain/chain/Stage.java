package demo.story.transactions.core.domain.chain;

import demo.story.transactions.core.domain.Context;

public abstract class Stage {

    private Stage next;

    public Stage linkWith(Stage next) {
        this.next = next;
        return next;
    }

    public abstract boolean execute(Context context);

    protected boolean executeNext(Context context) {
        if (next == null) {
            return true;
        }

        return next.execute(context);
    }
}
