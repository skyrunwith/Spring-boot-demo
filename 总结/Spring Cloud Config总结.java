Spring Cloud Config
	�ṩ�ͻ��˺ͷ���˵�֧��
	�ṩ��������������
	�����ļ��޸ĺ���Կ�����Ч
	�����ṩ��ͬ�汾�Ĺ���
	����֧�ֲ�ͬ�����ԣ�java��.Net��Delphi��node�ȣ�
	֧��һ�������Ĳ���
	�߿��ã���ֹ����崻��������ò����ã�
ʵս��
	���÷���ˣ�Git��ͨ�����ʹ�����������ļ����������ĸ߿���
	�ͻ��˷��������ļ���refresh
�����������ģ�
	1.�����������spring-cloud-config-server
	2.���ע�� @EnableConfigServer
	Ӧ�ã�
		���� Git��
			spring.cloud.config.server.git.[uri, searchPaths, username, password] 
		���ù������:
			/{application}-{profiles}[/{label}]
			/{application}-{profiles}.[yml,properties,json.and etc]
			[/{label}]/{application}-{profiles}.[yml,properties,json.and etc]
	ԭ��ʵ����ͨ��git clone ����������ݸ�����һ���ڱ��ش洢��Ȼ���ȡ��Щ���ݲ����ظ�΢����Ӧ�ý��м���
	3.�����ܹ���Զ��GIT�ֿ⡢����Git�ֿ⡢Config-server��Config-Client[ServiceA��ServiceB]
�ͻ������ã�
	1.�����������spring-cloud-starter-config
	2.bootstrap.properties ��ʵ���ⲿ���������� spring.cloud.config.[name, uri, lable, profile]
	Ӧ�ã�
		spring.cloud.config.name: ��Ӧ�����ļ���{application}����
		spring.cloud.config.profile����Ӧ{profile}����
		spring.cloud.config.label����Ӧgit�ķ�֧�������������ʹ�õ��Ǳ��ش洢����ò�������
		spring.cloud.config.uri���������ĵľ����ַ
		spring.cloud.config.discovery.service-id��ָ���������ĵ�service-id��������չΪ�߿������ü�Ⱥ��
	ԭ�����̣�
		Ӧ������ʱ������bootstrap.properties�����õ�Ӧ����{application}��������{profile}����֧��{label}����ConfigServer�����ȡ������Ϣ
		Config Server�����Լ�ά����Git�ֿ���Ϣ�Ϳͻ��˴��ݹ��������ö�λ��Ϣȥ����������Ϣ
		ͨ�� git clone �����ҵ���������Ϣ���ص�Config Server���ļ�ϵͳ��
		Config Server ���� Spring ��ApplicationContext ʵ��������Git���زֿ���������ļ��������Щ�������ݶ�ȡ�������ظ��ͻ���Ӧ��
		�ͻ���Ӧ���ڻ���ⲿ�����ļ�����ص��ͻ��˵�ApplicationContextʵ�������������ݵ����ȼ����ڿͻ��˵�Jar���ڲ��������ݣ�����Jar�����ظ������ݽ����ٱ�����
	4.Git���òֿ�
		�������ã�spring.cloud.config.server.git.[uri, searchPaths, username, password]��uri��ͨ��Զ�����ã�Ҳ����ͨ�������ļ� File:// ����
		ռλ��URI��{application}��{profile}��{label}
			{application}��ͨ���ͻ���spring.application.name����config server���Ӷ�����΢����Ӧ�õ����Զ�̬��ȡ��ͬλ�õ�����
			ͨ����URI��ʹ��ռλ�����԰������ǹ滮��ʵ��ͨ�õĲֿ����ã�
				����⣺http://git.oschina.net/didispace/member-service
				���ÿ⣺http://git.oschina.net/didispace/member-service-config
				git uri���ã�http://git.oschina.net/didispace/{application}-config
		���ö���ֿ�
			spring.cloud.config.server.git.repos�����ã�
				spring.cloud.config.server.git.dev.pattern=dev/*                        */
				spring.cloud.config.server.git.dev.uri=file://home/git/config-repo  
		��Ŀ¼�洢
			spring.cloud.config.server.git.searchPaths
		����Ȩ�ޣ�������HTTP�ķ�ʽ������֤����Ҫ���� username �� password �����������˻�
	5.SVN���òֿ�
		����svnkit������ͨ��spring.cloud.config.server.svn.[uri, username, password]
	6.���زֿ�
		ʹ����Git��SVN�ֿ���ļ�����Config Server�ı����ļ�ϵͳ�д洢һ�ݣ�Ĭ�ϴ洢����config-repoΪǰ׺����ʱĿ¼�У�����/tmp/config-repo-<�����>��Ŀ¼
		ָ�����ش洢Ŀ¼��spring.cloud.config.server.[git, svn].basedir
	7.�����ļ�ϵͳ
		���� spring.profiles.active=navitve��config server ��Ĭ�ϴ�Ӧ�õ� src/main/resource Ŀ¼�����������ļ�
		ָ�����������ļ�·����spring.cloud.config.server.native.searchLocations
	8.�������
	9.���Ը���
		��Config Server������ spring.cloud.config.server.override.[key]=[value],�ͻ��˴�Config Server�л�ȡ������Ϣʱ����ȡ����Щ��Ϣ��ͨ����ģʽ������Ϊ�ͻ����ṩĬ��ֵ
		�ͻ���Ҳ�����ø������ȼ������÷�ʽ����ѡ���Ƿ�ʹ��ConfigServer�ṩ��Ĭ��ֵ
	10.��ȫ����
		�����������Ĵ洢�����ݱȽ����У�������Ҫһ���İ�ȫ��ʩ�����Spring Security���������Ľ��а�ȫ����
		����security������������security.user.name�� security.user.password
	11.���ܽ���
		Spring Cloud Config �ṩ�����Խ��мӽ��ܵĹ��ܣ��Ա��������ļ�����Ϣ��ȫ
		����������ʱδ�����˽�
	12.�߿�������
		��ͳģʽ�������е�Config Server��ָ��ͬһ��Git�ֿ⣬���е��������ݾ�ͨ��ͳһ�Ĺ����ļ�ϵͳ��ά����
		����ģʽ����Config Server��Ϊһ����ͨ��΢����Ӧ�ã�����Eureka�ķ���������ϵ��
		����ˣ�config serverע�ᵽEureka
		�ͻ��ˣ�config client ע�ᵽEureka,����spring.cloud.config.discovery.service-id,spring.cloud.config.discovery.enable=true,spring.cloud.config.profile
	13.ʧ�ܿ�����Ӧ������
		ʧ�ܿ�����Ӧ:spring.cloud.config.failFast=true
		����:����spring-retry �� spring-boot-starter-aop
		����:spring.cloud.config.retry.[multiplier: 1000, initial-interval, max-interval:2000, max-attenpts:6]
	14.��̬ˢ�����ã�
		�ͻ������� spring-boot-starter-actuator
		@RefreshScope
		���� /refresh �˵�
https://gitee.com/hjj520/spring-cloud-2.x.git
https://www.cnblogs.com/babycomeon/p/11123850.html		