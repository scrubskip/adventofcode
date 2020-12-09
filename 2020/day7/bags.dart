import 'dart:convert';
import 'dart:io';

import "package:args/args.dart";

void main(List<String> args) {
  final parser = ArgParser();
  ArgResults argResults = parser.parse(args);
  getBags(new File(argResults.rest[0])
          .openRead()
          .transform(utf8.decoder)
          .transform(new LineSplitter()))
      .then((bags) {
    stdout.writeln(getOuterBags(bags, "shiny gold").length);
  });
}

final RegExp BAG_MATCHER = new RegExp(r"^(\d+) (.*) bags?$");

Future<Map<String, Bag>> getBags(Stream<String> rules) async {
  Map<String, Bag> bags = new Map();
  await for (String rule in rules) {
    // sample:
    // light red bags contain 1 bright white bag, 2 muted yellow bags.
    List<String> parts = rule.split(" bags contain ");
    String parentName = parts[0];
    Bag parentBag = bags[parentName];
    if (parentBag == null) {
      parentBag = new Bag(parentName);
      bags.putIfAbsent(parentName, () => parentBag);
    }
    if (parts[1] != "no other bags.") {
      // now parse the parts
      List<String> bagParts =
          parts[1].substring(0, parts[1].length - 1).split(", ");
      bagParts.forEach((bagPart) {
        RegExpMatch match = BAG_MATCHER.firstMatch(bagPart);
        if (match != null) {
          String name = match[2];
          Bag candidateBag = bags[name];
          if (candidateBag == null) {
            candidateBag = new Bag(name);
          }
          candidateBag.addParentBag(parentBag);
          bags[name] = candidateBag;
        }
      });
    }
  }
  return bags;
}

Set<String> getOuterBags(Map<String, Bag> bags, String name) {
  Set<String> outerBags = new Set();
  List<Bag> bagsToCheck = new List();
  bagsToCheck.add(bags[name]);
  while (bagsToCheck.isNotEmpty) {
    Bag bag = bagsToCheck.removeAt(0);
    bag.parentBags.forEach((key, parentBag) {
      if (!outerBags.contains(key)) {
        outerBags.add(key);
        bagsToCheck.add(parentBag);
      }
    });
  }
  return outerBags;
}

class Bag {
  final String name;
  Map<String, Bag> parentBags = new Map();

  Bag(this.name);

  void addParentBag(Bag parentBag) {
    if (!parentBags.containsKey(parentBag.name)) {
      parentBags[parentBag.name] = parentBag;
    }
  }
}
