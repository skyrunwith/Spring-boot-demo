Spring 缓存数据：Spring自身没有实现缓存的解决方案，它对缓存功能提供了声明式支持。Spring通过围绕方法创建一个缓存切面实现。
	1.@EnableCaching：开启缓存
	2.内置缓存管理器：SimpleCacheManager、NoOpCacheManager、ConcurrentMapCacheManager、CompositeCacheManager、 EhCacheCacheManager
	3.其他缓存管理器：RedisCacheManager
	4.缓存注解：@Cacheable、@CachePut、@CacheEvict、@Caching、allEntries、beforeInvocation
	5.缓存属性：value、condition、key、unless
	6.要求：与 @Cacheable和@CachePut不同，@CacheEvict能够应用在返回值为 void 的方法上，而@Cacheable和@CachePut需要非 void 的返回值，它将会作
为放在缓存中的条目。因为 @CacheEvict  只是将条目从缓存中移除，因此它可以放在任意的方法上，甚至 void 方法
	7.xml缓存条件：你可能会觉得在自己的源码中添加 Spring 的注解有点不太舒服；你需要在没有源码的 bean 上应用缓存功能