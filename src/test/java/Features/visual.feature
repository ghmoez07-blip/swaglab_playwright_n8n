Feature: visual
@conforme
Scenario: Vérification visuelle de l'icône panier
Given je suis sur la page dacceuil
Then l'icône du panier doit être graphiquement conforme

Scenario: Vérification visuelle du succès de commande
Given je suis sur la page dacceuil
And je termine un achat complet
Then la page de confirmation ne doit pas avoir de décalage visuel