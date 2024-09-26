/*
 * Copyright 2023 OpenSPG Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 */

package com.antgroup.openspg.reasoner.thinker

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

import com.antgroup.openspg.reasoner.KGDSLParser._
import com.antgroup.openspg.reasoner.parser.LexerInit
import com.antgroup.openspg.reasoner.thinker.logic.graph.Triple
import com.antgroup.openspg.reasoner.thinker.logic.rule._
import com.antgroup.openspg.reasoner.thinker.logic.rule.exact.{Condition, Or}

class SimplifyThinkerParser {
  var param: Map[String, Object] = Map.empty
  var thinkerRuleParser: ThinkerRuleParser = new ThinkerRuleParser()

  private var conditionToElementMap: mutable.HashMap[Condition, mutable.HashSet[ClauseEntry]] =
    new mutable.HashMap()

  def parseSimplifyDsl(
      simplifyDSL: String,
      param: Map[String, Object] = Map.empty): List[Rule] = {
    val parser = new LexerInit().initKGReasonerParser(simplifyDSL)
    this.param = param
    conditionToElementMap = mutable.HashMap()
    thinkerRuleParser = new ThinkerRuleParser()
    parseScript(parser.thinker_script())
  }

  def parseScript(ctx: Thinker_scriptContext): List[Rule] = {
    val ruleResult: mutable.ListBuffer[Rule] = mutable.ListBuffer.empty
    if (ctx.define_rule_on_concept() != null && ctx.define_rule_on_concept().size() > 0) {
      ctx
        .define_rule_on_concept()
        .asScala
        .foreach(rule => {
          ruleResult += parseDefineRuleOnConcept(rule)
        })
    }
    if (ctx.define_rule_on_relation_to_concept() != null
      && ctx.define_rule_on_relation_to_concept().size() > 0) {
      ctx
        .define_rule_on_relation_to_concept()
        .asScala
        .foreach(rule => {
          ruleResult += parseDefineRuleOnRelationToConcept(rule)
        })
    }
    if (ctx.define_proiority_rule_on_concept() != null
      && ctx.define_proiority_rule_on_concept().size() > 0) {
      ctx
        .define_proiority_rule_on_concept()
        .asScala
        .foreach(rule => {
          ruleResult += parseDefinePriorityRuleOnConcept(rule)
        })
    }
    ruleResult.toList
  }

  def parseDefineRuleOnConcept(ctx: Define_rule_on_conceptContext): Rule = {
    val rule = new Rule()
    val concept_nameContext =
      ctx.define_rule_on_concept_structure().concept_declaration().concept_name()
    rule.setHead(thinkerRuleParser.get_concept_full_form(concept_nameContext))
    if (null != ctx.description()) {
      rule.setDesc(ctx.description().unbroken_character_string_literal().getText)
    }
    val ruleAndAction: Rule_and_action_bodyContext =
      ctx.define_rule_on_concept_structure().rule_and_action_body()
    parseRuleAndAction(ruleAndAction, rule)
    rule
  }

  def parseRuleAndAction(ruleAndAction: Rule_and_action_bodyContext, rule: Rule): Unit = {
    if (ruleAndAction.rule_body_content() != null) {
      val multiLogicalStatementContext = ruleAndAction.rule_body_content().logical_statement()
      if (multiLogicalStatementContext != null && multiLogicalStatementContext.size() > 0) {
        val (root, body) = parseMultiLogicalStatement(multiLogicalStatementContext.asScala.toList)
        rule.setRoot(root)
        rule.setBody(body.asJava)
      }
    }
  }

  def parseMultiLogicalStatement(
      ctx: List[Logical_statementContext]): (Node, List[ClauseEntry]) = {
    val body: ListBuffer[ClauseEntry] = new mutable.ListBuffer[ClauseEntry]()
    if (ctx.length > 1) {
      val orChildrenList: ListBuffer[Node] = new mutable.ListBuffer[Node]()
      ctx.foreach(logicalStatement => {
        orChildrenList += parseOneLogicalStatement(logicalStatement, body)
      })

      val or = new Or()
      or.setChildren(orChildrenList.toList.asJava)
      (or, body.distinct.toList)
    } else {
      (parseOneLogicalStatement(ctx.head, body), body.distinct.toList)
    }
  }

  def parseOneLogicalStatement(
      ctx: Logical_statementContext,
      body: ListBuffer[ClauseEntry]): Node = {
    val node = thinkerRuleParser.thinkerParseValueExpression(ctx.value_expression(), body)
    conditionToElementMap ++= thinkerRuleParser.conditionToElementMap
    node
  }

  def parseDefineRuleOnRelationToConcept(ctx: Define_rule_on_relation_to_conceptContext): Rule = {
    val rule = new Rule()
    val spoRule = ctx.define_rule_on_relation_to_concept_structure().spo_rule()
    val (subject, predicate, object_) = thinkerRuleParser.parseSpoRule(spoRule, true)
    rule.setHead(new TriplePattern(new Triple(subject, predicate, object_)))
    if (ctx.description() != null) {
      rule.setDesc(ctx.description().unbroken_character_string_literal().getText)
    }
    val ruleAndAction: Rule_and_action_bodyContext =
      ctx.define_rule_on_relation_to_concept_structure().rule_and_action_body()
    parseRuleAndAction(ruleAndAction, rule)
    rule
  }

  def parseDefinePriorityRuleOnConcept(ctx: Define_proiority_rule_on_conceptContext): Rule = {
    throw new UnsupportedOperationException("DefinePriority not support yet")
  }

  def getConditionToElementMap(): Map[Condition, Set[ClauseEntry]] = {
    conditionToElementMap.toMap.map(x => (x._1, x._2.toSet))
  }

}
