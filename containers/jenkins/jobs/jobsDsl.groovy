multibranchPipelineJob('vis-case-processing-etl-nonprod') {
    branchSources {
        git {
            remote('git@git.uscis.dhs.gov:Wyntuition/containerized-spring-boot-jenkins-pipeline-scaffold')
            excludes('production-*')
        }
    }
    triggers {
        periodic(1)
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(5)
        }
    }
}