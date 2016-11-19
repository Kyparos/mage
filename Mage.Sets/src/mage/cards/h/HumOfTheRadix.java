/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Pete Rossi
 */
public class HumOfTheRadix extends CardImpl {

    public HumOfTheRadix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // Each artifact spell costs {1} more to cast for each artifact its controller controls.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HumOfTheRadixCostIncreaseEffect()));

    }

    public HumOfTheRadix(final HumOfTheRadix card) {
        super(card);
    }

    @Override
    public HumOfTheRadix copy() {
        return new HumOfTheRadix(this);
    }
}

class HumOfTheRadixCostIncreaseEffect extends CostModificationEffectImpl {

    HumOfTheRadixCostIncreaseEffect() {
        super(Duration.Custom, Outcome.Detriment, CostModificationType.INCREASE_COST);
        staticText = "Each artifact spell costs {1} more to cast for each artifact its controller controls.";
    }

    HumOfTheRadixCostIncreaseEffect(final HumOfTheRadixCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("artifacts you control");
        int additionalCost = game.getBattlefield().getActivePermanents(filter, abilityToModify.getControllerId(), game).size();
        if (additionalCost > 0) {
            CardUtil.increaseCost(abilityToModify, additionalCost);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            MageObject sourceObject = abilityToModify.getSourceObject(game);
            if (sourceObject != null && sourceObject.getCardType().contains(CardType.ARTIFACT)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public HumOfTheRadixCostIncreaseEffect copy() {
        return new HumOfTheRadixCostIncreaseEffect(this);
    }
}