<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/* @var $this yii\web\View */
/* @var $model frontend\models\Schedules */

$this->title = $model->car->brand." ".$model->car->model." (".$model->company->companyname." - ".$model->schedulingdate.")";

\yii\web\YiiAsset::register($this);
?>
<div class="schedules-view">
    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Back', "javascript:history.back()", ['class' => 'btn btn-light']) ?>
        <?= Html::a('Update', ['update', 'id' => $model->id], ['class' => 'btn btn-secondary']) ?>
        <?= Html::a('Delete', ['delete', 'id' => $model->id], [
            'class' => 'btn btn-dark',
            'data' => [
                'confirm' => 'Are you sure you want to delete this item?',
                'method' => 'post',
            ],
        ]) ?>
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            //'id',
            [
                'label' => 'Car',
                'value' => $model->car->registration
            ],
            [
                'label' => 'Company',
                'value' => $model->company->companyname
            ],
            //'currentdate',
            'schedulingdate',
            'repairdescription',
            'state',
            'repairtype',
        ],
    ]) ?>

</div>
