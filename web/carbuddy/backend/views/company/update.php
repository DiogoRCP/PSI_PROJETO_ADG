<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model backend\models\Companies */

$this->title = 'Update Company: ' . $model->companyname.' ('.$model->nif.')';
?>
<div class="companies-update">
    <div class="companies-content">
        <h1><?= Html::encode($this->title) ?></h1>
        <p>
            <?= Html::a('Back', ['index'], ['class' => 'btn btn-primary']) ?>
        </p>

        <?= $this->render('_form', [
            'model' => $model,
        ]) ?>
    </div>
</div>
