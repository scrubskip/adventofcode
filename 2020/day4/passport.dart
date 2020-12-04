import 'dart:async';
import 'dart:collection';
import 'dart:convert';
import "dart:io";

import "package:args/args.dart";

void main(List<String> args) {
  final parser = ArgParser();

  ArgResults argResults = parser.parse(args);
  getPassportStream(new File(argResults.rest[0])
          .openRead()
          .transform(utf8.decoder)
          .transform(new LineSplitter()))
      .where(isStrictlyValid)
      .length
      .then((result) => stdout.writeln(result));
}

Stream<String> getListAsStream(List<String> input) async* {
  for (var value in input) {
    yield value;
  }
}

Stream<Map<String, String>> getPassportStream(Stream<String> source) async* {
  Map<String, String> passport = new HashMap();

  await for (var chunk in source) {
    if (chunk.isEmpty) {
      // We got a new chunk, make a new passport.
      yield passport;
      passport = new HashMap();
    } else {
      // otherwise, split this into pieces and add to the hashmap.
      chunk.split(" ").forEach((element) {
        List<String> atoms = element.split(":");
        passport[atoms[0]] = atoms[1];
      });
    }
  }
  if (passport.isNotEmpty) {
    yield passport;
  }
}

final List<String> REQUIRED_FIELDS = [
  "byr",
  "iyr",
  "ecl",
  "eyr",
  "hcl",
  "hgt",
  "pid"
];

final RegExp HAIR_COLOR_MATCHER = new RegExp(r"#[a-f0-9]{6}");

final RegExp PID_MATCHER = new RegExp(r"[0-9]{9}");

final Set<String> VALID_EYE_COLORS =
    Set.of(["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]);

bool isValid(Map<String, String> passport) {
  return REQUIRED_FIELDS
          .where((element) => passport.containsKey(element))
          .length ==
      REQUIRED_FIELDS.length;
}

bool isStrictlyValid(Map<String, String> passport) {
  return REQUIRED_FIELDS
          .where((element) => checkField(passport[element], element))
          .length ==
      REQUIRED_FIELDS.length;
}

/**
 * Rules:
 * byr (Birth Year) - four digits; at least 1920 and at most 2002.
 * iyr (Issue Year) - four digits; at least 2010 and at most 2020.
 * eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
 * hgt (Height) - a number followed by either cm or in:
 * If cm, the number must be at least 150 and at most 193.
 * If in, the number must be at least 59 and at most 76.
 * hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
 * ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
 * pid (Passport ID) - a nine-digit number, including leading zeroes.
 * cid (Country ID) - ignored, missing or not.
 */

bool checkField(String value, String key) {
  if (value == null) {
    return false;
  }
  switch (key) {
    case "byr":
      int byr = int.tryParse(value);
      return byr >= 1920 && byr <= 2002;
    case "iyr":
      int iyr = int.tryParse(value);
      return iyr >= 2010 && iyr <= 2020;
    case "eyr":
      int eyr = int.tryParse(value);
      return eyr >= 2020 && eyr <= 2030;
    case "hgt":
      if (value.length < 3) {
        return false;
      }
      int hgt = int.tryParse(value.substring(0, value.length - 2));
      if (value.endsWith("cm")) {
        return hgt >= 150 && hgt <= 193;
      } else if (value.endsWith("in")) {
        return hgt >= 59 && hgt <= 76;
      }
      return false;
    case "hcl":
      return HAIR_COLOR_MATCHER.hasMatch(value);
    case "ecl":
      return VALID_EYE_COLORS.contains(value);
    case "pid":
      return value.length == 9 && PID_MATCHER.hasMatch(value);
    default:
      return false;
  }
}
