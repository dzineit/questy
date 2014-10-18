var builder = QuestBuilder.begin(questManager, "SimpleQuest").description("A very simple description.");

var obj1 = builder.objective("Simple1").description("The first simple objective.");
obj1.outcome("Outcome1").description("The first objective's first outcome.").type("run_5_mins");
obj1.outcome("Outcome2").description("The first objective's second outcome.").type("walk_5_mins");

var obj2 = builder.objective("Simple2").description("The second simple objective.");
obj2.outcome("Outcome1").description("The first objective's only outcome.").type("standstill_10_mins");
