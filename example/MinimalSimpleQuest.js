return QuestBuilder.begin(questManager, "SimpleQuest").description("A very simple description.")
            .objective("Simple1").description("The first simple objective.")
                .outcome("Outcome1").description("The first objective's first outcome.").type("run_5_mins").objective()
                .outcome("Outcome2").description("The first objective's second outcome.").type("walk_5_mins").quest()
            .objective("Simple2").description("The second simple objective.")
                .outcome("Outcome1").description("The first objective's only outcome.").type("standstill_10_mins").quest()
        .build();
