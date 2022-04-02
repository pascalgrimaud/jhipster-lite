import { ProjectService } from '@/springboot/domain/ProjectService';
import { GeneratorVue } from '@/springboot/primary';
import { mount, VueWrapper } from '@vue/test-utils';
import { stubProjectService } from '../domain/ProjectService.fixture';
import { ProjectToUpdate } from '@/springboot/primary/ProjectToUpdate';
import { createProjectToUpdate } from './ProjectToUpdate.fixture';
import { AngularService } from '@/springboot/domain/client/AngularService';
import { stubAngularService } from '../domain/client/AngularService.fixture';
import { stubReactService } from '../domain/client/ReactService.fixture';
import { ReactService } from '@/springboot/domain/client/ReactService';
import { stubSpringBootService } from '../domain/SpringBootService.fixture';
import { SpringBootService } from '@/springboot/domain/SpringBootService';
import { stubVueService } from '../domain/client/VueService.fixture';
import { VueService } from '@/springboot/domain/client/VueService';
import { stubLogger } from '../../common/domain/Logger.fixture';
import { Logger } from '@/common/domain/Logger';

let wrapper: VueWrapper;

interface WrapperOptions {
  angularService: AngularService;
  logger: Logger;
  projectService: ProjectService;
  reactService: ReactService;
  springBootService: SpringBootService;
  vueService: VueService;
}

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { angularService, logger, projectService, reactService, springBootService, vueService }: WrapperOptions = {
    angularService: stubAngularService(),
    logger: stubLogger(),
    projectService: stubProjectService(),
    reactService: stubReactService(),
    springBootService: stubSpringBootService(),
    vueService: stubVueService(),
    ...wrapperOptions,
  };
  wrapper = mount(GeneratorVue, {
    global: {
      provide: {
        angularService,
        logger,
        projectService,
        reactService,
        springBootService,
        vueService,
      },
    },
  });
};

const fillFullForm = async (projectToUpdate: ProjectToUpdate): Promise<void> => {
  const projectPathInput = wrapper.find('#path');
  await projectPathInput.setValue(projectToUpdate.folder);
  const baseNameInput = wrapper.find('#basename');
  await baseNameInput.setValue(projectToUpdate.baseName);
  const projectNameInput = wrapper.find('#projectname');
  await projectNameInput.setValue(projectToUpdate.projectName);
  const packageNameInput = wrapper.find('#packagename');
  await packageNameInput.setValue(projectToUpdate.packageName);
  const serverPortInput = wrapper.find('#serverport');
  await serverPortInput.setValue(projectToUpdate.serverPort);
};

describe('Generator', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should not init project when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.init.resolves({});
    await wrap({ projectService });

    const initButton = wrapper.find({ ref: 'init' });
    expect(initButton.exists()).toBe(true);
    await initButton.trigger('click');

    expect(projectService.init.called).toBe(false);
  });

  it('should init project when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.init.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#init');
    await initButton.trigger('click');

    const args = projectService.init.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on init failure', async () => {
    const logger = stubLogger();
    const projectService = stubProjectService();
    projectService.init.rejects({});
    await wrap({ projectService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#init');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Project initialization failed');
  });

  it('should not add Maven when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addMaven.resolves({});
    await wrap({ projectService });

    const button = wrapper.find('#maven');
    await button.trigger('click');

    expect(projectService.addMaven.called).toBe(false);
  });

  it('should add Maven when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addMaven.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#maven');
    await button.trigger('click');

    const args = projectService.addMaven.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding maven failure', async () => {
    const logger = stubLogger();
    const projectService = stubProjectService();
    projectService.addMaven.rejects({});
    await wrap({ projectService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#maven');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Maven to project failed');
  });

  it('should not add JavaBase when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addJavaBase.resolves({});
    await wrap({ projectService });

    const button = wrapper.find('#javabase');
    await button.trigger('click');

    expect(projectService.addJavaBase.called).toBe(false);
  });

  it('should add JavaBase when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addJavaBase.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#javabase');
    await button.trigger('click');

    const args = projectService.addJavaBase.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding java base failure', async () => {
    const logger = stubLogger();
    const projectService = stubProjectService();
    projectService.addJavaBase.rejects({});
    await wrap({ projectService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#javabase');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Java Base to project failed');
  });

  it('should not add SpringBoot when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBoot.resolves({});
    await wrap({ springBootService });

    const button = wrapper.find('#springboot');
    await button.trigger('click');

    expect(springBootService.addSpringBoot.called).toBe(false);
  });

  it('should add SpringBoot when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBoot.resolves({});
    await wrap({ springBootService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#springboot');
    await button.trigger('click');

    const args = springBootService.addSpringBoot.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding spring boot failure', async () => {
    const logger = stubLogger();
    const springBootService = stubSpringBootService();
    springBootService.addSpringBoot.rejects({});
    await wrap({ springBootService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#springboot');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding SpringBoot to project failed');
  });

  it('should not add SpringBoot MVC with Tomcat when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootMvcTomcat.resolves({});
    await wrap({ springBootService });

    const button = wrapper.find('#springbootmvctomcat');
    await button.trigger('click');

    expect(springBootService.addSpringBootMvcTomcat.called).toBe(false);
  });

  it('should add SpringBoot MVC with Tomcat when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootMvcTomcat.resolves({});
    await wrap({ springBootService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#springbootmvctomcat');
    await button.trigger('click');

    const args = springBootService.addSpringBootMvcTomcat.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding SpringBoot MVC with Tomcat failure', async () => {
    const logger = stubLogger();
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootMvcTomcat.rejects({});
    await wrap({ springBootService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#springbootmvctomcat');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding SpringBoot MVC with Tomcat to project failed');
  });

  it('should not add Angular when project path is not filled', async () => {
    const angularService = stubAngularService();
    angularService.add.resolves({});
    await wrap({ angularService });

    const button = wrapper.find('#angular');
    await button.trigger('click');

    expect(angularService.add.called).toBe(false);
  });

  it('should add Angular when project path is filled', async () => {
    const angularService = stubAngularService();
    angularService.add.resolves({});
    await wrap({ angularService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#angular');
    await button.trigger('click');

    const args = angularService.add.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should add Angular with Style when checkbox is checked', async () => {
    const angularService = stubAngularService();
    angularService.addWithStyle.resolves({});
    await wrap({ angularService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const checkbox = wrapper.find('#angular-with-style');
    await checkbox.setValue(true);
    const button = wrapper.find('#angular');
    await button.trigger('click');

    const args = angularService.addWithStyle.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding Angular failure', async () => {
    const logger = stubLogger();
    const angularService = stubAngularService();
    angularService.add.rejects({});
    await wrap({ angularService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#angular');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Angular to project failed');
  });

  it('should handle error on adding Angular with style failure', async () => {
    const logger = stubLogger();
    const angularService = stubAngularService();
    angularService.addWithStyle.rejects({});
    await wrap({ angularService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const checkbox = wrapper.find('#angular-with-style');
    await checkbox.setValue(true);
    const initButton = wrapper.find('#angular');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Angular with style to project failed');
  });

  it('should not add React when project path is not filled', async () => {
    const reactService = stubReactService();
    reactService.add.resolves({});
    await wrap({ reactService });

    const button = wrapper.find('#react');
    await button.trigger('click');

    expect(reactService.add.called).toBe(false);
  });

  it('should add React when project path is filled', async () => {
    const reactService = stubReactService();
    reactService.add.resolves({});
    await wrap({ reactService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#react');
    await button.trigger('click');

    const args = reactService.add.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should add React with Style when checkbox is checked', async () => {
    const reactService = stubReactService();
    reactService.addWithStyle.resolves({});
    await wrap({ reactService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const checkbox = wrapper.find('#react-with-style');
    await checkbox.setValue(true);
    const button = wrapper.find('#react');
    await button.trigger('click');

    const args = reactService.addWithStyle.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding React failure', async () => {
    const logger = stubLogger();
    const reactService = stubReactService();
    reactService.add.rejects({});
    await wrap({ reactService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#react');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding React to project failed');
  });

  it('should handle error on adding React with style failure', async () => {
    const logger = stubLogger();
    const reactService = stubReactService();
    reactService.addWithStyle.rejects({});
    await wrap({ reactService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const checkbox = wrapper.find('#react-with-style');
    await checkbox.setValue(true);
    const initButton = wrapper.find('#react');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding React with style to project failed');
  });

  it('should not add Vue when project path is not filled', async () => {
    const vueService = stubVueService();
    vueService.add.resolves({});
    await wrap({ vueService });

    const button = wrapper.find('#vue');
    await button.trigger('click');

    expect(vueService.add.called).toBe(false);
  });

  it('should add Vue when project path is filled', async () => {
    const vueService = stubVueService();
    vueService.add.resolves({});
    await wrap({ vueService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#vue');
    await button.trigger('click');

    const args = vueService.add.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should add Vue with Style when checkbox is checked', async () => {
    const vueService = stubVueService();
    vueService.addWithStyle.resolves({});
    await wrap({ vueService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const checkbox = wrapper.find('#vue-with-style');
    await checkbox.setValue(true);
    const button = wrapper.find('#vue');
    await button.trigger('click');

    const args = vueService.addWithStyle.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding Vue failure', async () => {
    const logger = stubLogger();
    const vueService = stubVueService();
    vueService.add.rejects({});
    await wrap({ vueService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#vue');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Vue to project failed');
  });

  it('should handle error on adding Vue with style failure', async () => {
    const logger = stubLogger();
    const vueService = stubVueService();
    vueService.addWithStyle.rejects({});
    await wrap({ vueService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const checkbox = wrapper.find('#vue-with-style');
    await checkbox.setValue(true);
    const initButton = wrapper.find('#vue');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Vue with style to project failed');
  });

  it('should not add Frontend Maven Plugin when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addFrontendMavenPlugin.resolves({});
    await wrap({ projectService });

    const button = wrapper.find('#frontend-maven-plugin');
    await button.trigger('click');

    expect(projectService.addFrontendMavenPlugin.called).toBe(false);
  });

  it('should add Frontend Maven Plugin when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addFrontendMavenPlugin.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#frontend-maven-plugin');
    await button.trigger('click');

    const args = projectService.addFrontendMavenPlugin.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding Frontend Maven Plugin failure', async () => {
    const logger = stubLogger();
    const projectService = stubProjectService();
    projectService.addFrontendMavenPlugin.rejects({});
    await wrap({ projectService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#frontend-maven-plugin');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Frontend Maven Plugin to project failed');
  });
});
