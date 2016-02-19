package io.beanmapper.builders;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

public abstract class AbstractBuilder<E> {

    public JpaRepository<E, Long> repository;
    protected E entity;
    private Supplier<E> constructor;

    public AbstractBuilder(JpaRepository<E, Long> repository, Supplier<E> constructor) {
        this.repository = repository;
        this.constructor = constructor;
        this.entity = makeNewInstance();
    }

    protected E init(E entity) {
        this.entity = makeNewInstance();
        return entity;
    }

    public E build() {
        return init(this.entity);
    }

    public E makeNewInstance() {
        return constructor.get();
    }

    public E save() {
        return repository.saveAndFlush(init(this.entity));
    }

    public AbstractBuilder<E> setEntity(E entity) {
        this.entity = entity;
        return this;
    }
}
