<template>
  <q-layout view="hHh lpR fFf">
    <q-header reveal elevated class="bg-primary text-white">
      <q-toolbar class="glossy">
        <q-avatar>
          <img src="../../../content/JHipster-Lite-neon-blue_40x.png" alt="" />
        </q-avatar>

        <q-toolbar-title>JHipster Lite</q-toolbar-title>
        <q-space />

        <q-btn flat round dense icon="fa-brands fa-twitter" class="q-mr-xs" />
        <q-btn flat round dense icon="fa-brands fa-github" class="q-mr-xs" />
        <!-- waiting for Dark Plugin integration
        <q-separator vertical color="white" inset />
        <q-toggle
        v-model="theme"
        checked-icon="nightlight_round"
        unchecked-icon="light_mode"
        label="Dark theme" />
        -->
      </q-toolbar>
    </q-header>

    <q-page-container>
      <q-page class="q-pa-md">
        <div class="row q-gutter-md">
          <div class="col-12 col-md-4">
            <q-card flat bordered class="my-card">
              <q-toolbar class="shadow-2">
                <q-toolbar-title>Project configuration</q-toolbar-title>
              </q-toolbar>

              <q-separator color="secondary" insert />

              <q-card-section>
                <q-input v-model="project.folder" id="path" label="Path (required)" />
                <q-input v-model="project.baseName" id="basename" label="Base name" />
                <q-input v-model="project.projectName" id="projectname" label="Project name" />
                <q-input v-model="project.packageName" id="packagename" label="Package name" />
                <q-input v-model="project.serverPort" id="serverport" label="Server port" />
              </q-card-section>
            </q-card>
          </div>
          <div class="col">
            <q-card class="my-card col-12" flat bordered>
              <q-toolbar class="shadow-2">
                <q-toolbar-title>Options</q-toolbar-title>
              </q-toolbar>
              <q-separator color="secondary" insert />
              <q-list bordered padding>
                <q-item-label header class="text-weight-bold">Language</q-item-label>

                <q-separator spaced inset="item" />
                <div class="row">
                  <q-item>
                    <q-item-section side top>
                      <q-radio v-model="language" val="java" />
                    </q-item-section>
                    <q-item-section>
                      <q-item-label>Java</q-item-label>
                    </q-item-section>
                    <q-item-section top avatar>
                      <q-avatar>
                        <img src="../../../content/java_rounded_40x40.png" alt="" />
                      </q-avatar>
                    </q-item-section>
                  </q-item>
                </div>

                <q-separator spaced />

                <q-item-label header class="text-weight-bold">Build tool</q-item-label>

                <q-separator spaced inset="item" />
                <div class="row">
                  <q-item>
                    <q-item-section side top>
                      <q-radio v-model="build" val="maven" />
                    </q-item-section>
                    <q-item-section>
                      <q-item-label>Maven</q-item-label>
                    </q-item-section>
                    <q-item-section top avatar>
                      <q-avatar>
                        <img src="../../../content/MavenLogo.png" alt="" />
                      </q-avatar>
                    </q-item-section>
                  </q-item>

                  <q-separator vertical inset />

                  <q-item>
                    <q-item-section side top>
                      <q-radio v-model="build" val="gradle" />
                    </q-item-section>
                    <q-item-section>
                      <q-item-label>Gradle</q-item-label>
                    </q-item-section>
                    <q-item-section top avatar>
                      <q-avatar>
                        <img src="../../../content/GradleLogo.png" alt="" />
                      </q-avatar>
                    </q-item-section>
                  </q-item>
                </div>

                <q-separator spaced />

                <q-item-label header class="text-weight-bold">Server</q-item-label>

                <q-separator spaced inset="item" />

                <div class="row">
                  <q-item>
                    <q-item-section side top>
                      <q-radio v-model="server" val="none" />
                    </q-item-section>
                    <q-item-section>
                      <q-item-label>None</q-item-label>
                    </q-item-section>
                    <q-item-section top avatar>
                      <q-avatar>
                        <img src="../../../content/JHipster-Lite-neon-blue_40x.png" alt="" />
                      </q-avatar>
                    </q-item-section>
                  </q-item>

                  <q-separator vertical inset />

                  <q-item>
                    <q-item-section side top>
                      <q-radio v-model="server" val="spring" />
                    </q-item-section>
                    <q-item-section>
                      <q-item-label>Spring Boot</q-item-label>
                    </q-item-section>
                    <q-item-section top avatar>
                      <q-avatar>
                        <img src="../../../content/SpringLogo.png" alt="" />
                      </q-avatar>
                    </q-item-section>
                  </q-item>
                </div>

                <q-separator spaced />

                <q-item-label header class="text-weight-bold">Client</q-item-label>

                <q-separator spaced inset="item" />
                <div class="row">
                  <q-item>
                    <q-item-section side top>
                      <q-radio v-model="client" val="none" />
                    </q-item-section>
                    <q-item-section>
                      <q-item-label>None</q-item-label>
                    </q-item-section>
                    <q-item-section top avatar>
                      <q-avatar>
                        <img src="../../../content/JHipster-Lite-neon-blue_40x.png" alt="" />
                      </q-avatar>
                    </q-item-section>
                  </q-item>

                  <q-separator vertical inset />

                  <q-item>
                    <q-item-section side top>
                      <q-radio v-model="client" val="angular" />
                    </q-item-section>
                    <q-item-section>
                      <q-item-label>Angular</q-item-label>
                    </q-item-section>
                    <q-item-section top avatar>
                      <q-avatar>
                        <img src="../../../content/AngularLogo.png" alt="" />
                      </q-avatar>
                    </q-item-section>
                  </q-item>

                  <q-separator vertical inset />

                  <q-item>
                    <q-item-section side top>
                      <q-radio v-model="client" val="react" />
                    </q-item-section>
                    <q-item-section>
                      <q-item-label>React</q-item-label>
                    </q-item-section>
                    <q-item-section top avatar>
                      <q-avatar>
                        <img src="../../../content/ReactLogo.png" alt="" />
                      </q-avatar>
                    </q-item-section>
                  </q-item>

                  <q-separator vertical inset />

                  <q-item>
                    <q-item-section side top>
                      <q-radio v-model="client" val="vue" />
                    </q-item-section>
                    <q-item-section>
                      <q-item-label>Vue</q-item-label>
                    </q-item-section>
                    <q-item-section top avatar>
                      <q-avatar>
                        <img src="../../../content/VueLogo.png" alt="" />
                      </q-avatar>
                    </q-item-section>
                  </q-item>

                  <q-separator vertical inset />

                  <q-item>
                    <q-item-section side top>
                      <q-radio v-model="client" val="svelte" />
                    </q-item-section>

                    <q-item-section>
                      <q-item-label>Svelte</q-item-label>
                    </q-item-section>

                    <q-item-section top avatar>
                      <q-avatar>
                        <img src="../../../content/SvelteLogo.png" alt="" />
                      </q-avatar>
                    </q-item-section>
                  </q-item>
                </div>
              </q-list>
            </q-card>
          </div>
        </div>

        <div>
          <q-splitter v-model="splitterModel">
            <template v-slot:before>
              <q-tabs v-model="tab" vertical class="text-teal">
                <q-tab class="text-secondary" name="initialization" icon="fa-solid fa-trowel-bricks" label="Initialization" />
                <q-tab class="text-spring" name="spring" icon="fa-solid fa-leaf" label="Spring Boot" />
                <q-tab class="text-vue" name="vue" icon="fa-brands fa-vuejs" label="Vue" />
                <q-tab class="text-angular" name="angular" icon="fa-brands fa-angular" label="Angular" />
              </q-tabs>
            </template>

            <template v-slot:after>
              <q-tab-panels v-model="tab" animated swipeable vertical transition-prev="jump-up" transition-next="jump-up">
                <q-tab-panel name="initialization">
                  <q-card q-card class="my-card col-12" flat bordered>
                    <q-toolbar>
                      <q-toolbar-title class="text-h5 text-secondary">Initialization</q-toolbar-title>
                    </q-toolbar>
                    <q-separator color="secondary" insert />
                    <q-list bordered class="row q-pt-none">
                      <q-item>
                        <q-item-section>
                          <q-item-label class="text-weight-bold">Init</q-item-label>
                        </q-item-section>
                        <q-item-section top side>
                          <div class="text-grey-8 q-gutter-xs">
                            <q-btn
                              id="init"
                              ref="init"
                              name="init"
                              @click="initProject"
                              class="glossy"
                              align="between"
                              size="md"
                              color="primary"
                              icon="engineering"
                            >
                              Generate
                            </q-btn>
                          </div>
                        </q-item-section>
                      </q-item>

                      <q-separator vertical />

                      <q-item>
                        <q-item-section>
                          <q-item-label class="text-weight-bold">Maven</q-item-label>
                        </q-item-section>
                        <q-item-section top side>
                          <div class="text-grey-8 q-gutter-xs">
                            <q-btn id="maven" class="glossy" align="between" size="md" color="primary" icon="engineering"> Generate </q-btn>
                          </div>
                        </q-item-section>
                      </q-item>

                      <q-separator vertical />

                      <q-item>
                        <q-item-section>
                          <q-item-label class="text-weight-bold">Java base</q-item-label>
                        </q-item-section>
                        <q-item-section top side>
                          <div class="text-grey-8 q-gutter-xs">
                            <q-btn id="javabase" class="glossy" align="between" size="md" color="primary" icon="engineering">
                              Generate
                            </q-btn>
                          </div>
                        </q-item-section>
                      </q-item>

                      <q-separator vertical />
                    </q-list>
                  </q-card>
                </q-tab-panel>

                <q-tab-panel name="spring">
                  <q-card q-card class="my-card col-12" flat bordered>
                    <q-toolbar>
                      <q-toolbar-title class="text-h5 text-spring">Spring Boot</q-toolbar-title>
                    </q-toolbar>
                    <q-separator class="bg-spring" insert />

                    <q-card-actions>
                      <q-btn rounded flat color="dark" label="Core" />
                      <q-btn rounded color="primary" label="Security" />
                      <q-btn rounded flat color="dark" label="Database" />
                      <q-btn rounded flat color="dark" label="Cache" />
                      <q-btn rounded flat color="dark" label="Cloud" />
                      <q-btn rounded flat color="dark" label="Tools" />

                      <q-space />

                      <q-btn
                        color="grey"
                        round
                        flat
                        dense
                        :icon="expanded ? 'keyboard_arrow_up' : 'keyboard_arrow_down'"
                        @click="expanded = !expanded"
                      />
                    </q-card-actions>

                    <q-slide-transition>
                      <div v-show="expanded">
                        <q-separator />
                        <q-list bordered class="row q-pt-none">
                          <q-item>
                            <q-item-section>
                              <q-item-label class="text-h6">xxx</q-item-label>
                            </q-item-section>
                            <q-item-section top side>
                              <div class="text-grey-8 q-gutter-xs">
                                <q-btn class="glossy" align="between" size="md" color="primary" icon="engineering"> Generate </q-btn>
                              </div>
                            </q-item-section>
                          </q-item>

                          <q-separator vertical />

                          <q-item>
                            <q-item-section>
                              <q-item-label class="text-h6">xxx</q-item-label>
                            </q-item-section>
                            <q-item-section top side>
                              <div class="text-grey-8 q-gutter-xs">
                                <q-btn class="glossy" align="between" size="md" color="primary" icon="engineering"> Generate </q-btn>
                              </div>
                            </q-item-section>
                          </q-item>

                          <q-separator vertical />

                          <q-item>
                            <q-item-section>
                              <q-item-label class="text-h6">xxx</q-item-label>
                            </q-item-section>
                            <q-item-section top side>
                              <div class="text-grey-8 q-gutter-xs">
                                <q-btn class="glossy" align="between" size="md" color="primary" icon="engineering"> Generate </q-btn>
                              </div>
                            </q-item-section>
                          </q-item>

                          <q-separator vertical />
                        </q-list>
                      </div>
                    </q-slide-transition>
                  </q-card>
                </q-tab-panel>

                <q-tab-panel name="vue">
                  <q-card q-card class="my-card col-12" flat bordered>
                    <q-toolbar>
                      <q-toolbar-title class="text-h5 text-vue">Vue</q-toolbar-title>
                    </q-toolbar>
                    <q-separator class="bg-vue" insert />
                    <q-card-actions>
                      <q-btn rounded flat color="dark" label="xxx" />
                      <q-btn rounded color="primary" label="xxx" />
                      <q-btn rounded flat color="dark" label="xxx" />

                      <q-space />

                      <q-btn
                        color="grey"
                        round
                        flat
                        dense
                        :icon="expanded ? 'keyboard_arrow_up' : 'keyboard_arrow_down'"
                        @click="expanded = !expanded"
                      />
                    </q-card-actions>

                    <q-slide-transition>
                      <div v-show="expanded">
                        <q-separator />
                        <q-list bordered class="row q-pt-none">
                          <q-item>
                            <q-item-section>
                              <q-item-label class="text-h6">xxx</q-item-label>
                            </q-item-section>
                            <q-item-section top side>
                              <div class="text-grey-8 q-gutter-xs">
                                <q-btn class="glossy" align="between" size="md" color="primary" icon="engineering"> Generate </q-btn>
                              </div>
                            </q-item-section>
                          </q-item>

                          <q-separator vertical />

                          <q-item>
                            <q-item-section>
                              <q-item-label class="text-h6">xxxx</q-item-label>
                            </q-item-section>
                            <q-item-section top side>
                              <div class="text-grey-8 q-gutter-xs">
                                <q-btn class="glossy" align="between" size="md" color="primary" icon="engineering"> Generate </q-btn>
                              </div>
                            </q-item-section>
                          </q-item>
                        </q-list>
                      </div>
                    </q-slide-transition>
                  </q-card>
                </q-tab-panel>
                <q-tab-panel name="angular">
                  <q-card q-card class="my-card col-12" flat bordered>
                    <q-toolbar>
                      <q-toolbar-title class="text-h5 text-angular">Angular</q-toolbar-title>
                    </q-toolbar>
                    <q-separator class="bg-angular" insert />
                    <q-card-actions>
                      <q-btn rounded flat color="dark" label="xxx" />
                      <q-btn rounded color="primary" label="xxx" />
                      <q-btn rounded flat color="dark" label="xxx" />

                      <q-space />

                      <q-btn
                        color="grey"
                        round
                        flat
                        dense
                        :icon="expanded ? 'keyboard_arrow_up' : 'keyboard_arrow_down'"
                        @click="expanded = !expanded"
                      />
                    </q-card-actions>

                    <q-slide-transition>
                      <div v-show="expanded">
                        <q-separator />
                        <q-list bordered class="row q-pt-none">
                          <q-item>
                            <q-item-section>
                              <q-item-label class="text-h6">xxx</q-item-label>
                            </q-item-section>
                            <q-item-section top side>
                              <div class="text-grey-8 q-gutter-xs">
                                <q-btn class="glossy" align="between" size="md" color="primary" icon="engineering"> Generate </q-btn>
                              </div>
                            </q-item-section>
                          </q-item>

                          <q-separator vertical />

                          <q-item>
                            <q-item-section>
                              <q-item-label class="text-h6">xxxx</q-item-label>
                            </q-item-section>
                            <q-item-section top side>
                              <div class="text-grey-8 q-gutter-xs">
                                <q-btn class="glossy" align="between" size="md" color="primary" icon="engineering"> Generate </q-btn>
                              </div>
                            </q-item-section>
                          </q-item>
                        </q-list>
                      </div>
                    </q-slide-transition>
                  </q-card>
                </q-tab-panel>
              </q-tab-panels>
            </template>
          </q-splitter>
        </div>
      </q-page>
      <router-view />
    </q-page-container>

    <q-footer reveal elevated class="bg-grey-8 text-white">
      <q-toolbar>
        <q-avatar>
          <img src="../../../content/JHipster-Lite-neon-blue_40x.png" alt="" />
        </q-avatar>
        <q-toolbar-title>JHipster Lite</q-toolbar-title>
      </q-toolbar>
    </q-footer>
  </q-layout>
</template>

<script lang="ts" src="./Generator.component.ts"></script>

<style>
.bg-spring {
  background: #6db33f !important;
}
.text-spring {
  color: #6db33f !important;
}
.bg-vue {
  background: #3fb27f !important;
}
.text-vue {
  color: #3fb27f !important;
}
.bg-angular {
  background: #c3002f !important;
}
.text-angular {
  color: #c3002f !important;
}
</style>
