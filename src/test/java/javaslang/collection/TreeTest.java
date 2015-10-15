/*     / \____  _    _  ____   ______  / \ ____  __    _ _____
 *    /  /    \/ \  / \/    \ /  /\__\/  //    \/  \  / /  _  \   Javaslang
 *  _/  /  /\  \  \/  /  /\  \\__\\  \  //  /\  \ /\\/  \__/  /   Copyright 2014-now Daniel Dietrich
 * /___/\_/  \_/\____/\_/  \_/\__\/__/___\_/  \_//  \__/_____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.collection;

import javaslang.Serializables;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.Value;
import javaslang.collection.Tree.Node;
import javaslang.control.Option;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.IterableAssert;
import org.assertj.core.api.ObjectAssert;
import org.junit.Test;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;

import static javaslang.Serializables.deserialize;
import static javaslang.Serializables.serialize;

/**
 * Tests all methods defined in {@link javaslang.collection.Tree}.
 */
public class TreeTest extends AbstractTraversableTest {

    /**
     * <pre><code>
     *         1
     *        / \
     *       /   \
     *      /     \
     *     2       3
     *    / \     /
     *   4   5   6
     *  /       / \
     * 7       8   9
     * </code></pre>
     */
    final Tree<Integer> tree = $(1, $(2, $(4, $(7)), $(5)), $(3, $(6, $(8), $(9))));

    @Override
    protected <T> IterableAssert<T> assertThat(Iterable<T> actual) {
        return new IterableAssert<T>(actual) {
            @SuppressWarnings("unchecked")
            @Override
            public IterableAssert<T> isEqualTo(Object expected) {
                if (actual instanceof Option) {
                    final Option<?> opt1 = ((Option<?>) actual);
                    final Option<?> opt2 = (Option<?>) expected;
                    Assertions.assertThat(convOption(opt1)).isEqualTo(convOption(opt2));
                } else if(expected instanceof Map) {
                    final Map<?,?> map1 = (Map<?,?>) actual;
                    final Map<?,?> map2 = (Map<?,?>) expected;
                    Assertions.assertThat(convMap(map1)).isEqualTo(convMap(map2));
                } else if(expected instanceof Tree) {
                    assertThat(Stream.ofAll(actual)).isEqualTo(Stream.ofAll((Tree) expected));
                } else {
                    Assertions.assertThat(actual).isEqualTo((Iterable<T>) expected);
                }
                return this;
            }

            private Option<?> convOption(Option<?> option) {
                return option.map(o -> (o instanceof Iterable) ? Stream.ofAll((Iterable<?>) o) : o);
            }

            private Map<?,?> convMap(Map<?,?> map) {
                return map.map((k, v) -> Map.Entry.of(k, v instanceof Iterable ? Stream.ofAll((Iterable<?>) v) : v));
            }
        };
    }

    @Override
    protected <T> ObjectAssert<T> assertThat(T actual) {
        return new ObjectAssert<T>(actual) {
            @Override
            public ObjectAssert<T> isEqualTo(Object expected) {
                if (actual instanceof Tuple2) {
                    final Tuple2<?, ?> t1 = (Tuple2<?, ?>) actual;
                    final Tuple2<?, ?> t2 = (Tuple2<?, ?>) expected;
                    assertThat((Iterable<?>) t1._1).isEqualTo(t2._1);
                    assertThat((Iterable<?>) t1._2).isEqualTo(t2._2);
                    return this;
                } else {
                    return super.isEqualTo(expected);
                }
            }
        };
    }

    @Override
    protected <T> Collector<T, ArrayList<T>, Tree<T>> collector() {
        return Tree.collector();
    }

    @Override
    protected <T> Tree<T> empty() {
        return Tree.empty();
    }

    @Override
    protected <T> Node<T> of(T element) {
        return Tree.of(element);
    }

    @SuppressWarnings("varargs")
    @SafeVarargs
    @Override
    protected final <T> Tree<T> of(T... elements) {
        return Tree.ofAll(List.of(elements));
    }

    @Override
    protected <T> Tree<T> ofAll(Iterable<? extends T> elements) {
        return Tree.ofAll(elements);
    }

    @Override
    protected Tree<Boolean> ofAll(boolean[] array) {
        return Tree.ofAll(List.ofAll(array));
    }

    @Override
    protected Tree<Byte> ofAll(byte[] array) {
        return Tree.ofAll(List.ofAll(array));
    }

    @Override
    protected Tree<Character> ofAll(char[] array) {
        return Tree.ofAll(List.ofAll(array));
    }

    @Override
    protected Tree<Double> ofAll(double[] array) {
        return Tree.ofAll(List.ofAll(array));
    }

    @Override
    protected Tree<Float> ofAll(float[] array) {
        return Tree.ofAll(List.ofAll(array));
    }

    @Override
    protected Tree<Integer> ofAll(int[] array) {
        return Tree.ofAll(List.ofAll(array));
    }

    @Override
    protected Tree<Long> ofAll(long[] array) {
        return Tree.ofAll(List.ofAll(array));
    }

    @Override
    protected Tree<Short> ofAll(short[] array) {
        return Tree.ofAll(List.ofAll(array));
    }

    @Override
    boolean useIsEqualToInsteadOfIsSameAs() {
        return true;
    }

    @Override
    int getPeekNonNilPerformingAnAction() {
        return 1;
    }

    @SuppressWarnings("varargs")
    @SafeVarargs
    protected final <T> Node<T> $(T value, Node<T>... children) {
        return Tree.of(value, children);
    }

    // -- Tree test

    @Test
    public void shouldInstantiateTreeBranchWithOf() {
        final Tree<Integer> actual = Tree.of(1, Tree.of(2), Tree.of(3));
        final Tree<Integer> expected = new Node<>(1, List.of(new Node<>(2, List.empty()), new Node<>(3, List.empty())));
        assertThat(actual).isEqualTo(expected);
    }

    // -- Leaf test

    @Test
    public void shouldInstantiateTreeLeafWithOf() {
        final Tree<Integer> actual = Tree.of(1);
        final Tree<Integer> expected = new Node<>(1, List.empty());
        assertThat(actual).isEqualTo(expected);
    }

    // -- Node test

    @Test
    public void shouldCreateANodeWithoutChildren() {
        new Node<>(1, List.empty());
    }

    @Test(expected = InvalidObjectException.class)
    public void shouldNotCallReadObjectOnNodeInstance() throws Throwable {
        Serializables.callReadObject(tree);
    }

    // -- getValue

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotGetValueOfNil() {
        Tree.empty().getValue();
    }

    @Test
    public void shouldNotGetValueOfNonNil() {
        assertThat(tree.getValue()).isEqualTo(1);
    }

    // -- isEmpty

    @Test
    public void shouldIdentifyNilAsEmpty() {
        assertThat(Tree.empty().isEmpty()).isTrue();
    }

    @Test
    public void shouldIdentifyNonNilAsNotEmpty() {
        assertThat(tree.isEmpty()).isFalse();
    }

    // -- isLeaf

    @Test
    public void shouldIdentifiyLeafAsLeaf() {
        assertThat($(0).isLeaf()).isTrue();
    }

    @Test
    public void shouldIdentifyNonLeafAsNonLeaf() {
        assertThat(tree.isLeaf()).isFalse();
    }

    @Test
    public void shouldIdentifiyNilAsNonLeaf() {
        assertThat(Tree.empty().isLeaf()).isFalse();
    }

    // -- isBranch

    @Test
    public void shouldIdentifiyLeafAsNonBranch() {
        assertThat($(0).isBranch()).isFalse();
    }

    @Test
    public void shouldIdentifyNonLeafAsBranch() {
        assertThat(tree.isBranch()).isTrue();
    }

    @Test
    public void shouldIdentifiyNilAsNonBranch() {
        assertThat(Tree.empty().isBranch()).isFalse();
    }

    // -- getChildren

    @Test
    public void shouldGetChildrenOfLeaf() {
        assertThat($(0).getChildren()).isEqualTo(List.empty());
    }

    @Test
    public void shouldGetChildrenOfBranch() {
        final List<? extends Tree<Integer>> children = tree.getChildren();
        assertThat(children.length()).isEqualTo(2);
        assertThat(children.get(0).toString()).isEqualTo("(2 (4 7) 5)");
        assertThat(children.get(1).toString()).isEqualTo("(3 (6 8 9))");
    }

    @Test
    public void shouldIGetChildrenOfNil() {
        assertThat(Tree.empty().getChildren()).isEqualTo(List.empty());
    }

    // -- branchCount

    @Test
    public void shouldCountBranchesOfNil() {
        assertThat(Tree.empty().branchCount()).isEqualTo(0);
    }

    @Test
    public void shouldCountBranchesOfNonNil() {
        assertThat(tree.branchCount()).isEqualTo(5);
    }

    // -- leafCount

    @Test
    public void shouldCountLeavesOfNil() {
        assertThat(Tree.empty().leafCount()).isEqualTo(0);
    }

    @Test
    public void shouldCountLeavesOfNonNil() {
        assertThat(tree.leafCount()).isEqualTo(4);
    }

    // -- nodeCount

    @Test
    public void shouldCountNodesOfNil() {
        assertThat(Tree.empty().nodeCount()).isEqualTo(0);
    }

    @Test
    public void shouldCountNodesOfNonNil() {
        assertThat(tree.nodeCount()).isEqualTo(9);
    }

    // -- contains

    @Test
    public void shouldNotFindNodeInNil() {
        assertThat(Tree.empty().contains(1)).isFalse();
    }

    @Test
    public void shouldFindExistingNodeInNonNil() {
        assertThat(tree.contains(5)).isTrue();
    }

    @Test
    public void shouldNotFindNonExistingNodeInNonNil() {
        assertThat(tree.contains(0)).isFalse();
    }

    // -- drop

    @Test
    @Override
    public void shouldDropNoneIfCountIsNegative() {
        assertThat(of(1, 2, 3).drop(-1)).isEqualTo(of(1, 2, 3));
    }

    // -- dropRight

    @Test
    @Override
    public void shouldDropRightNoneIfCountIsNegative() {
        assertThat(of(1, 2, 3).dropRight(-1)).isEqualTo(of(1, 2, 3));
    }

    // -- flatMap

    @Test
    public void shouldFlatMapEmptyTree() {
        assertThat(Tree.empty().flatMap(t -> Tree.of(1))).isEqualTo(Tree.empty());
    }

    @Test
    public void shouldFlatMapNonEmptyTree() {
        final Node<Integer> testee = $(1, $(2), $(3));
        final Tree<Integer> actual = testee.flatMap(i -> $(i, $(i), $(i)));
        final Tree<Integer> expected = $(1, $(1), $(1), $(2, $(2), $(2)), $(3, $(3), $(3)));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Override
    public void shouldFlatMapTraversableByExpandingElements() {
        assertThat(of(1, 2, 3).flatMap(i -> {
            if (i == 1) {
                return of(1, 2, 3);
            } else if (i == 2) {
                return of(4, 5);
            } else {
                return of(6);
            }
        })).isEqualTo($(1, $(2), $(3), $(4, $(5)), $(6)));
    }

    @Test
    @Override
    public void shouldFlatMapElementsToSequentialValuesInTheRightOrder() {
        final AtomicInteger seq = new AtomicInteger(0);
        final Value<Integer> actualInts = $(0, $(1), $(2))
                .flatMap(ignored -> of(seq.getAndIncrement(), seq.getAndIncrement()));
        final Value<Integer> expectedInts = $(0, $(1), $(2, $(3)), $(4, $(5)));
        assertThat(actualInts).isEqualTo(expectedInts);
    }

    // -- flatten

    @Test
    public void shouldFlattenEmptyTree() {
        assertThat(Tree.empty().flatten()).isEmpty();
    }

    @Test
    @Override
    public void shouldFlattenTraversableOfTraversables() {

        // (((1 1 1) 2 3) ((4 4 4) 5 6) ((7 7 7) 8 9))
        final Tree<?> testee = $($($(1, $(1), $(1)), $(2), $(3)), $($(4, $(4), $(4)), $(5), $(6)),
                $($(7, $(7), $(7)), $(8), $(9)));
        final Tree<?> actual = testee.flatten();

        // (1 1 1 2 3 (4 4 4 5 6) (7 7 7 8 9))
        final Tree<?> expected = $(1, $(1), $(1), $(2), $(3), $(4, $(4), $(4), $(5), $(6)),
                $(7, $(7), $(7), $(8), $(9)));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Override
    public void shouldFlattenTraversableOfTraversablesAndPlainElements() {
        assertThat($(1, $($(2, $(3)), $(4)), $(5)).flatten()).isEqualTo($(1, $(2, $(3), $(4)), $(5)));
    }

    @Test
    @Override
    public void shouldFlattenDifferentElementTypes() {
        final Tree<?> testee = $(1, $($("2", $(3.1415, $(1L)))));
        final Tree<?> actual = testee.flatten();
        final Tree<?> expected = $(1, $("2", $(3.1415, $(1L))));
        assertThat(actual).isEqualTo(expected);
    }

    // -- iterator

    @Override
    @Test
    public void shouldNotHasNextWhenNilIterator() {
        assertThat(Tree.empty().iterator().hasNext()).isFalse();
    }

    @Override
    @Test(expected = NoSuchElementException.class)
    public void shouldThrowOnNextWhenNilIterator() {
        Tree.empty().iterator().next();
    }

    @Override
    @Test
    public void shouldIterateFirstElementOfNonNil() {
        assertThat(tree.iterator().next()).isEqualTo(1);
    }

    @Override
    @Test
    public void shouldFullyIterateNonNil() {
        final int length = List
                .of(1, 2, 4, 7, 5, 3, 6, 8, 9)
                .zip(tree::iterator)
                .filter(t -> Objects.equals(t._1, t._2))
                .length();
        assertThat(length).isEqualTo(9);
    }

    // -- map

    @Test
    public void shouldMapEmpty() {
        assertThat(Tree.empty().map(i -> i)).isEqualTo(Tree.empty());
    }

    @Test
    public void shouldMapTree() {
        assertThat(tree.map(i -> (char) (i + 64)).toString()).isEqualTo("(A (B (D G) E) (C (F H I)))");
    }

    // -- replace

    @Test
    public void shouldReplaceNullInEmpty() {
        assertThat(Tree.empty().replace(null, null)).isEmpty();
    }

    @Test
    public void shouldReplaceFirstOccurrenceUsingDepthFirstSearchInNonEmptyTree() {
        //   1        1
        //  / \  ->  / \
        // 2   3    99  3
        final Tree<Integer> testee = Tree.of(1, Tree.of(2), Tree.of(3));
        final Tree<Integer> actual = testee.replace(2, 99);
        final Tree<Integer> expected = Tree.of(1, Tree.of(99), Tree.of(3));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldNotReplaceAnyElementIfThereIsNoOccurrenceInNonEmptyTree() {
        final Tree<Integer> testee = Tree.of(1, Tree.of(2), Tree.of(3));
        final Tree<Integer> actual = testee.replace(4, 99);
        assertThat(actual).isEqualTo(testee);
    }

    // -- traverse

    @Test
    public void shouldTraverseEmpty() {
        assertThat(Tree.empty().traverse()).isEqualTo(empty());
    }

    // -- traverse(Order)

    @Test
    public void shouldTraverseTreeUsingPreOrder() {
        assertThat(tree.traverse(Tree.Order.PRE_ORDER)).isEqualTo(Stream.of(1, 2, 4, 7, 5, 3, 6, 8, 9));
    }

    @Test
    public void shouldTraverseTreeUsingInOrder() {
        assertThat(tree.traverse(Tree.Order.IN_ORDER)).isEqualTo(Stream.of(7, 4, 2, 5, 1, 8, 6, 9, 3));
    }

    @Test
    public void shouldTraverseTreeUsingPostOrder() {
        assertThat(tree.traverse(Tree.Order.POST_ORDER)).isEqualTo(Stream.of(7, 4, 5, 2, 8, 9, 6, 3, 1));
    }

    @Test
    public void shouldTraverseTreeUsingLevelOrder() {
        assertThat(tree.traverse(Tree.Order.LEVEL_ORDER)).isEqualTo(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    // -- unzip

    @Test
    public void shouldUnzipEmptyTree() {
        assertThat(Tree.empty().unzip(t -> Tuple.of(t, t))).isEqualTo(Tuple.of(Tree.empty(), Tree.empty()));
    }

    @Test
    public void shouldUnzipNonEmptyTree() {
        final Tree<Integer> testee = $(1, $(2), $(3));
        final Tuple2<Tree<Integer>, Tree<Integer>> actual = testee.unzip(i -> Tuple.of(i, -i));
        final Tuple2<Tree<Integer>, Tree<Integer>> expected = Tuple.of($(1, $(2), $(3)), $(-1, $(-2), $(-3)));
        assertThat(actual).isEqualTo(expected);
    }

    // equals

    @Test
    public void shouldBeAwareThatTwoTreesOfSameInstanceAreEqual() {
        // DEV_NOTE: intentionally not called `assertThat(Tree.empty()).isEqualTo(Tree.empty())`
        assertThat(Tree.empty().equals(Tree.empty())).isTrue();
    }

    @Test
    public void shouldBeAwareOfTwoDifferentEqualTrees() {
        assertThat($(0).equals($(0))).isTrue();
    }

    @Test
    public void shouldBeAwareThatTreeNotEqualsObject() {
        assertThat($(0)).isNotEqualTo(new Object());
    }

    // hashCode

    @Test
    public void shouldBeAwareThatHashCodeOfEmptyIsOne() {
        assertThat(Tree.empty().hashCode()).isEqualTo(1);
    }

    @Test
    public void shouldBeAwareThatHashCodeOfLeafIsGreaterThanOne() {
        assertThat($(0).hashCode()).isGreaterThan(1);
    }

    // toString

    @Test
    public void shouldReturnStringRepresentationOfEmpty() {
        assertThat(Tree.empty().toString()).isEqualTo("()");
    }

    @Test
    public void shouldReturnStringRepresentationOfNode() {
        assertThat(tree.toString()).isEqualTo("(1 (2 (4 7) 5) (3 (6 8 9)))");
    }

    // -- toLispString

    @Test
    public void shouldConvertEmptyToLispString() {
        assertThat(Tree.empty().toString()).isEqualTo("()");
    }

    @Test
    public void shouldConvertNonEmptyToLispString() {
        assertThat(tree.toString()).isEqualTo("(1 (2 (4 7) 5) (3 (6 8 9)))");
    }

    // -- Serializable interface

    @Test
    public void shouldSerializeDeserializeEmpty() {
        final Object actual = deserialize(serialize(Tree.empty()));
        final Object expected = Tree.empty();
        assertThat(actual).isEqualTo(expected);
    }

    @Override
    @Test
    public void shouldPreserveSingletonInstanceOnDeserialization() {
        final boolean actual = deserialize(serialize(Tree.empty())) == Tree.empty();
        assertThat(actual).isTrue();
    }

    @Test
    public void shouldSerializeDeserializeNonEmpty() {
        final Object actual = deserialize(serialize(tree));
        final Object expected = tree;
        assertThat(actual).isEqualTo(expected);
    }
}
