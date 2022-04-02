import { ProjectService } from '@/springboot/domain/ProjectService';
import { defineComponent, inject, ref } from 'vue';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { AngularService } from '@/springboot/domain/client/AngularService';
import { ReactService } from '@/springboot/domain/client/ReactService';
import { VueService } from '@/springboot/domain/client/VueService';
import { SpringBootService } from '@/springboot/domain/SpringBootService';
import { Logger } from '@/common/domain/Logger';

export default defineComponent({
  name: 'GeneratorComponent',
  components: {},
  setup() {
    const angularService = inject('angularService') as AngularService;
    const logger = inject('logger') as Logger;
    const projectService = inject('projectService') as ProjectService;
    const reactService = inject('reactService') as ReactService;
    const springBootService = inject('springBootService') as SpringBootService;
    const vueService = inject('vueService') as VueService;

    const project = ref<ProjectToUpdate>({
      folder: '',
    });
    const isAngularWithStyle = ref<boolean>(false);
    const isReactWithStyle = ref<boolean>(false);
    const isVueWithStyle = ref<boolean>(false);

    const client = ref<string>('');
    const server = ref<string>('');
    const build = ref<string>('');
    const language = ref<string>('');
    const expanded = ref<boolean>(false);
    const theme = ref<boolean>(false);
    const tab = ref<string>('initialization');
    const splitterModel = ref<number>(20);

    const initProject = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await projectService.init(toProject(project.value)).catch(error => logger.error('Project initialization failed', error));
      }
    };

    const addMaven = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await projectService.addMaven(toProject(project.value)).catch(error => logger.error('Adding Maven to project failed', error));
      }
    };

    const addJavaBase = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await projectService
          .addJavaBase(toProject(project.value))
          .catch(error => logger.error('Adding Java Base to project failed', error));
      }
    };

    const addSpringBoot = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addSpringBoot(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot to project failed', error));
      }
    };

    const addSpringBootMvcTomcat = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addSpringBootMvcTomcat(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot MVC with Tomcat to project failed', error));
      }
    };

    const addAngular = async (): Promise<void> => {
      if (project.value.folder !== '') {
        if (isAngularWithStyle.value) {
          await angularService
            .addWithStyle(toProject(project.value))
            .catch(error => logger.error('Adding Angular with style to project failed', error));
        } else {
          await angularService.add(toProject(project.value)).catch(error => logger.error('Adding Angular to project failed', error));
        }
      }
    };

    const addReact = async (): Promise<void> => {
      if (project.value.folder !== '') {
        if (isReactWithStyle.value) {
          await reactService
            .addWithStyle(toProject(project.value))
            .catch(error => logger.error('Adding React with style to project failed', error));
        } else {
          await reactService.add(toProject(project.value)).catch(error => logger.error('Adding React to project failed', error));
        }
      }
    };

    const addVue = async (): Promise<void> => {
      if (project.value.folder !== '') {
        if (isVueWithStyle.value) {
          await vueService
            .addWithStyle(toProject(project.value))
            .catch(error => logger.error('Adding Vue with style to project failed', error));
        } else {
          await vueService.add(toProject(project.value)).catch(error => logger.error('Adding Vue to project failed', error));
        }
      }
    };

    const addFrontendMavenPlugin = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await projectService
          .addFrontendMavenPlugin(toProject(project.value))
          .catch(error => logger.error('Adding Frontend Maven Plugin to project failed', error));
      }
    };

    return {
      project,
      isAngularWithStyle,
      isReactWithStyle,
      isVueWithStyle,
      initProject,
      addMaven,
      addJavaBase,
      addSpringBoot,
      addSpringBootMvcTomcat,
      addAngular,
      addReact,
      addVue,
      addFrontendMavenPlugin,
      client,
      server,
      build,
      language,
      expanded,
      theme,
      tab,
      splitterModel,
    };
  },
});
