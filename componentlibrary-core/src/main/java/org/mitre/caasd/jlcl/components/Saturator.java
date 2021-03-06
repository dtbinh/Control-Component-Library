/**
 * 
 */
package org.mitre.caasd.jlcl.components;

import org.mitre.caasd.jlcl.interfaces.ISaturator;
import org.mitre.caasd.jlcl.util.ClosedInterval;
import org.mitre.caasd.jlcl.util.ValueInterval;

/**
 * A saturation control component the will limit the minimum and maximum values
 * possible.
 * 
 * @author SBOWMAN
 * 
 * @param <NUMERICTYPE>
 *            The numeric type used for input and output.
 */
public class Saturator<NUMERICTYPE extends Number> extends ControlComponent<NUMERICTYPE, SimpleControlComponentArgs<NUMERICTYPE>> implements ISaturator<NUMERICTYPE> {

    private final ClosedInterval<NUMERICTYPE> interval;

    /**
     * @param clazz
     *            The numeric class, must extend from {@link Number}.
     * @param interval
     *            The {@link ValueInterval} object to use.
     */
    public Saturator(final Class<NUMERICTYPE> clazz, final ClosedInterval<NUMERICTYPE> interval) {
        super(clazz);
        this.interval = interval;
    }

    @Override
    protected double evaluateAsDoublePrimitive(SimpleControlComponentArgs<NUMERICTYPE> evaluationArguments) {
        // use double primitive
        NUMERICTYPE errorSignal = evaluationArguments.getErrorSignalValue();
        double errorSignalAsPrimitive = evaluationArguments.getErrorSignalValue().doubleValue();
        if (interval.isOnTheInterval(errorSignal)) {
            return errorSignalAsPrimitive;
        }
        if (interval.compare(errorSignal, interval.getUpperValue()) > 0) {
            // above the upper window boundary. return upper limit
            return interval.getUpperValue().doubleValue();
        } else {
            // if (interval.compare(errorSignal, interval.getLowerValue()) < 0)
            // {
            // below the lower window boundary. return lower limit
            return interval.getLowerValue().doubleValue();
        }
    }

    @Override
    protected long evaluateAsLongPrimitive(SimpleControlComponentArgs<NUMERICTYPE> evaluationArguments) {
        // use long primitive
        NUMERICTYPE errorSignal = evaluationArguments.getErrorSignalValue();
        long errorSignalAsPrimitive = evaluationArguments.getErrorSignalValue().longValue();
        if (interval.isOnTheInterval(errorSignal)) {
            return errorSignalAsPrimitive;
        }
        if (interval.compare(errorSignal, interval.getUpperValue()) > 0) {
            // above the upper window boundary. return upper limit
            return interval.getUpperValue().longValue();
        } else {
            // if (interval.compare(errorSignal, interval.getLowerValue()) < 0)
            // {
            // below the lower window boundary. return lower limit
            return interval.getLowerValue().longValue();
        }
    }

}
