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


class Genre(BuilderJob):
    def build(self):
        source = CSVReader(
            local_path="./builder/job/data/genre.csv",
            columns=["gid","gname"],
            start_row=2,
        )

        mapping = (
            SPGTypeMapping(spg_type_name=MovieMining.Genre)
            .add_property_mapping("gid", MovieMining.Genre.id)
            .add_property_mapping("gname", MovieMining.Genre.name)
        )

        sink = KGWriter()

        return source >> mapping >> sink