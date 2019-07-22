package br.com.digitoglobal.projeto.util.optionalList;

import javax.persistence.Transient;

import br.com.digitoglobal.projeto.bean.model.BaseEntity;

public abstract class OptionalModelEntity extends BaseEntity implements OptionalModelable {

    @Transient
    private OptionalModel optionalModel;

    public OptionalModelEntity() {
        this.optionalModel = new OptionalModel();
    }

    @Override
    public void select() {
        optionalModel.select();
    }

    @Override
    public void unSelect() {
        optionalModel.unSelect();
    }

    @Override
    public void removeTemporarily() {
        optionalModel.removeTemporarily();
    }

    @Override
    public void addedTemporarily() {
        optionalModel.addedTemporarily();
    }

    @Override
    public void reset() {
        optionalModel.reset();
    }

    @Override
    public void setOptionalModel(OptionalModel optionalModel) {
        this.optionalModel.setOptionalModel(optionalModel);
    }

    @Override
    public boolean isNotSelected() {
        return optionalModel.isNotSelected();
    }

    @Override
    public boolean isSelected() {
        return optionalModel.isSelected();
    }

    @Override
    public void setSelected(boolean selected) {
        optionalModel.setSelected(selected);
    }

    @Override
    public boolean isTemporarilyRemoved() {
        return optionalModel.isTemporarilyRemoved();
    }

    @Override
    public void setTemporarilyRemoved(boolean temporarilyRemoved) {
        optionalModel.setTemporarilyRemoved(temporarilyRemoved);
    }

    @Override
    public boolean isTemporarilyAdded() {
        return optionalModel.isTemporarilyAdded();
    }

    @Override
    public void setTemporarilyAdded(boolean temporarilyAdded) {
        optionalModel.setTemporarilyAdded(temporarilyAdded);
    }

}
