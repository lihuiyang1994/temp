package demo.docker.temp.config.sentinel.slot;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.slotchain.AbstractLinkedProcessorSlot;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;

/**
 * lihui
 * 2022/3/16
 * CustomerSlot
 *
 * @description
 */
public class CustomerSlot extends AbstractLinkedProcessorSlot<DefaultNode> {

    @Override
    public void entry(Context context, ResourceWrapper resourceWrapper, DefaultNode defaultNode, int i, boolean b, Object... objects) throws Throwable {

    }

    @Override
    public void exit(Context context, ResourceWrapper resourceWrapper, int i, Object... objects) {

    }
}
