package com.lexicalscope.svm.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;
import com.lexicalscope.svm.vm.StateSearch;

public class GoalTreeGuidedSearch<T, S> implements StateSearch<S> {
   private final GoalTreeCorrespondence<T, S> correspondence;
   private final List<GoalTreePair<T, S>> openChildren = new ArrayList<>();

   private final GoalExtractor<T, S> goalExtractor;
   private final List<S> result = new ArrayList<>();
   private final Randomiser randomiser;
   private boolean searchingQ = true;
   private GoalTreePair<T, S> correspondenceUnderConsideration;
   private boolean pInitialised;
   private boolean qInitialised;
   private S pending;

   public GoalTreeGuidedSearch(
         final GoalTreeCorrespondence<T, S> correspondence,
         final GoalExtractor<T, S> goalExtractor,
         final Randomiser randomiser) {
      this.correspondence = correspondence;
      openChildren.addAll(correspondence.children());
      this.goalExtractor = goalExtractor;
      this.randomiser = randomiser;
   }

   @Override public S pendingState() {
      if(pending == null) {
         return switchSides();
      }
      return pending;
   }

   private S switchSides() {
      System.out.println("switch");

      if(correspondenceUnderConsideration != null) {
         System.out.println("correspondence " + correspondenceUnderConsideration);
      }

      if(searchingQ &&
            correspondenceUnderConsideration != null &&
            correspondenceUnderConsideration.isOpen()) {
         System.out.println("still open");
         openChildren.add(correspondenceUnderConsideration);
      } else if (searchingQ && correspondenceUnderConsideration != null) {
         System.out.println("discarding");
      }

      if(searchingQ) {
         System.out.println("was searching q");
      } else {
         System.out.println("was searching p");
      }

      if(openChildren.isEmpty()) {
         System.out.println("not open");
      } else {
         System.out.println("open children = " + openChildren.size());
      }

      while(!searchingQ || !openChildren.isEmpty()) {
         System.out.println("change sides");
         searchingQ = !searchingQ;

         if(!searchingQ) {
            System.out.println("pick open");
            correspondenceUnderConsideration = randomOpenCorrespondence(randomiser);
         }

         if(!searchingQ && correspondenceUnderConsideration.psideIsOpen()) {
            System.out.println("pick p side");
            return pending = correspondenceUnderConsideration.openPNode(randomiser);
         }

         if(searchingQ && correspondenceUnderConsideration.qsideIsOpen()) {
            System.out.println("pick q side");
            return pending = correspondenceUnderConsideration.openQNode(randomiser);
         }
      }
      System.out.println("return null");
      return pending = null;

//      if(searchingQ) {
//         searchingQ = false;
//
//         if(correspondenceUnderConsideration != null && correspondenceUnderConsideration.isOpen()) {
//            openChildren.add(correspondenceUnderConsideration);
//         }
//
//         if(openChildren.isEmpty()) {
//            return null;
//         }
//
//         correspondenceUnderConsideration = randomOpenCorrespondence(randomiser);
//      }
//
//      if(!searchingQ && correspondenceUnderConsideration.pside().isOpen()) {
//         return pending = correspondenceUnderConsideration.openPNode(randomiser);
//      } else {
//         searchingQ = true;
//         if(correspondenceUnderConsideration.qside().isOpen()) {
//            return pending = correspondenceUnderConsideration.openQNode(randomiser);
//         } else {
//            return switchSides();
//         }
//      }
   }

   private GoalTreePair<T, S> randomOpenCorrespondence(final Randomiser randomiser) {
      final int index = randomiser.random(openChildren.size());
      final GoalTreePair<T, S> result = openChildren.get(index);
      openChildren.set(index, openChildren.get(openChildren.size() - 1));
      openChildren.remove(openChildren.size() - 1);
      assert result.isOpen();
      return result;
   }

   @Override public void reachedLeaf() {
      System.out.println("leaf");
      result.add(pending);
      switchSides();
   }

   @Override public void fork(final S[] states) {
      System.out.println("fork " + states.length);
      if(searchingQ) {
         correspondenceUnderConsideration.expandQ(states);
      } else {
         correspondenceUnderConsideration.expandP(states);
      }
      assert correspondenceUnderConsideration.isOpen();
      switchSides();
   }

   @Override public void goal() {
      System.out.println("goal");
      final GoalTreePair<T, S> newPair;
      if(searchingQ) {
         newPair = correspondence.
            reachedQ(
                  correspondenceUnderConsideration,
                  goalExtractor.goal(pending),
                  pending,
                  goalExtractor.pc(pending));
      } else {
         newPair = correspondence.
            reachedP(
                  correspondenceUnderConsideration,
                  goalExtractor.goal(pending),
                  pending,
                  goalExtractor.pc(pending));
      }
      if(newPair != null) {
         System.out.println("add pair");
         openChildren.add(newPair);
      }
      switchSides();
   }

   @Override public S firstResult() {
      return result.get(0);
   }

   @Override public Collection<S> results() {
      return result;
   }

   @Override public void consider(final S state) {
      if(!pInitialised) {
         correspondence.pInitial(state);
         pInitialised = true;
      } else if(!qInitialised) {
         correspondence.qInitial(state);
         qInitialised = true;
      } else {
         throw new IllegalStateException("only 2 initial states can be considered, not " + state);
      }
   }
}
