# -*- coding: utf-8 -*-
# Copyright 2023 OpenSPG Authors
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
# in compliance with the License. You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software distributed under the License
# is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
# or implied.

from knext.builder.model.builder_job import BuilderJob
from knext.builder.component import SPGTypeMapping
from knext.builder.component import CSVReader, KGWriter
from schema.moviemining_schema_helper import MovieMining

from knext.builder.component.mapping import RelationMapping


class Person(BuilderJob):
    def build(self):
        source = CSVReader(
            local_path="./builder/job/data/person.csv",
            columns=["pid","birth","death","name","biography","birthplace"],
            start_row=2,
        )

        mapping = (
            SPGTypeMapping(spg_type_name=MovieMining.Person)
            .add_property_mapping("pid", MovieMining.Person.id)
            .add_property_mapping("birth", MovieMining.Person.birthDate)
            .add_property_mapping("death", MovieMining.Person.deathDate)
            .add_property_mapping("name", MovieMining.Person.name)
            .add_property_mapping("biography", MovieMining.Person.biography)
            .add_property_mapping("birthplace", MovieMining.Person.birthPlace)
        )

        sink = KGWriter()

        return source >> mapping >> sink


class PersonActIn(BuilderJob):
    def build(self):
        source = CSVReader(
            local_path="./builder/job/data/person_to_movie.csv",
            columns=["pid", "mid"],
            start_row=2,
        )

        mapping = (
            RelationMapping(
                subject_name=MovieMining.Person,
                predicate_name="actIn",
                object_name=MovieMining.Movie,
            )
            .add_sub_property_mapping("pid", "srcId")
            .add_sub_property_mapping("mid", "dstId")
        )
        sink = KGWriter()

        return source >> mapping >> sink