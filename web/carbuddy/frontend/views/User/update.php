<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model frontend\models\Users */

$this->title = 'Update User: ' . $model->username.' ('.$model->nif.')';

?>
<div class="users-update">

    <div class="users-content">

    <h1><?= Html::encode($this->title) ?></h1>

        <p><?= Html::a('Back', "javascript:history.back()", ['class' => 'btn btn-light']) ?></p>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>
    </div>

</div>

