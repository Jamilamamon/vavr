/*     / \____  _    ______   _____ / \____   ____  _____
 *    /  \__  \/ \  / \__  \ /  __//  \__  \ /    \/ __  \   Javaslang
 *  _/  // _\  \  \/  / _\  \\_  \/  // _\  \  /\  \__/  /   Copyright 2014-2015 Daniel Dietrich
 * /___/ \_____/\____/\_____/____/\___\_____/_/  \_/____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.algebra;

/*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*\
   G E N E R A T O R   C R A F T E D
\*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/

import javaslang.Function1;
import javaslang.Function15;
import javaslang.Tuple15;

/**
 * <p>Defines a Functor by generalizing the map function which maps 15 elements.</p>
 *
 * All instances of the Functor15 interface should obey the two functor laws:
 * <ul>
 *     <li>{@code m.map(a -> a) ≡ m}</li>
 *     <li>{@code m.map(f.compose(g)) ≡ m.map(g).map(f)}</li>
 * </ul>
 * where {@code f, g ∈ Tuple15 → Tuple15}.
 *
 * @param <T1> Component type of this Functor.
 * @see <a href="http://www.haskellforall.com/2012/09/the-functor-design-pattern.html">The functor design pattern</a>
 */
public interface Functor15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> {

    /**
     * Applies a function f to the components of this functor.
     *
     * @param <U1> type of the 1st component of the resulting functor
     * @param <U2> type of the 2nd component of the resulting functor
     * @param <U3> type of the 3rd component of the resulting functor
     * @param <U4> type of the 4th component of the resulting functor
     * @param <U5> type of the 5th component of the resulting functor
     * @param <U6> type of the 6th component of the resulting functor
     * @param <U7> type of the 7th component of the resulting functor
     * @param <U8> type of the 8th component of the resulting functor
     * @param <U9> type of the 9th component of the resulting functor
     * @param <U10> type of the 10th component of the resulting functor
     * @param <U11> type of the 11th component of the resulting functor
     * @param <U12> type of the 12th component of the resulting functor
     * @param <U13> type of the 13th component of the resulting functor
     * @param <U14> type of the 14th component of the resulting functor
     * @param <U15> type of the 15th component of the resulting functor
     * @param f a 15-ary function which maps the components of this functor
     * @return a new functor with 15 component types U1, U2, U3, U4, U5, U6, U7, U8, U9, U10, U11, U12, U13, U14, U15.
     */
    <U1, U2, U3, U4, U5, U6, U7, U8, U9, U10, U11, U12, U13, U14, U15> Functor15<U1, U2, U3, U4, U5, U6, U7, U8, U9, U10, U11, U12, U13, U14, U15> map(Function15<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, Tuple15<? extends U1, ? extends U2, ? extends U3, ? extends U4, ? extends U5, ? extends U6, ? extends U7, ? extends U8, ? extends U9, ? extends U10, ? extends U11, ? extends U12, ? extends U13, ? extends U14, ? extends U15>> f);

    /**
     * Applies a separate function to each component of this functor.
     *
     * @param <U1> type of the 1st component of the resulting functor
     * @param <U2> type of the 2nd component of the resulting functor
     * @param <U3> type of the 3rd component of the resulting functor
     * @param <U4> type of the 4th component of the resulting functor
     * @param <U5> type of the 5th component of the resulting functor
     * @param <U6> type of the 6th component of the resulting functor
     * @param <U7> type of the 7th component of the resulting functor
     * @param <U8> type of the 8th component of the resulting functor
     * @param <U9> type of the 9th component of the resulting functor
     * @param <U10> type of the 10th component of the resulting functor
     * @param <U11> type of the 11th component of the resulting functor
     * @param <U12> type of the 12th component of the resulting functor
     * @param <U13> type of the 13th component of the resulting functor
     * @param <U14> type of the 14th component of the resulting functor
     * @param <U15> type of the 15th component of the resulting functor
     * @param f1 the function applied to the 1st component of this functor
     * @param f2 the function applied to the 2nd component of this functor
     * @param f3 the function applied to the 3rd component of this functor
     * @param f4 the function applied to the 4th component of this functor
     * @param f5 the function applied to the 5th component of this functor
     * @param f6 the function applied to the 6th component of this functor
     * @param f7 the function applied to the 7th component of this functor
     * @param f8 the function applied to the 8th component of this functor
     * @param f9 the function applied to the 9th component of this functor
     * @param f10 the function applied to the 10th component of this functor
     * @param f11 the function applied to the 11th component of this functor
     * @param f12 the function applied to the 12th component of this functor
     * @param f13 the function applied to the 13th component of this functor
     * @param f14 the function applied to the 14th component of this functor
     * @param f15 the function applied to the 15th component of this functor
     * @return a new functor with 15 component types U1, U2, U3, U4, U5, U6, U7, U8, U9, U10, U11, U12, U13, U14, U15.
     */
    <U1, U2, U3, U4, U5, U6, U7, U8, U9, U10, U11, U12, U13, U14, U15> Functor15<U1, U2, U3, U4, U5, U6, U7, U8, U9, U10, U11, U12, U13, U14, U15> map(Function1<? super T1, ? extends U1> f1, Function1<? super T2, ? extends U2> f2, Function1<? super T3, ? extends U3> f3, Function1<? super T4, ? extends U4> f4, Function1<? super T5, ? extends U5> f5, Function1<? super T6, ? extends U6> f6, Function1<? super T7, ? extends U7> f7, Function1<? super T8, ? extends U8> f8, Function1<? super T9, ? extends U9> f9, Function1<? super T10, ? extends U10> f10, Function1<? super T11, ? extends U11> f11, Function1<? super T12, ? extends U12> f12, Function1<? super T13, ? extends U13> f13, Function1<? super T14, ? extends U14> f14, Function1<? super T15, ? extends U15> f15);
}